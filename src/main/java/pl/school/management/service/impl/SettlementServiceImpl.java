package pl.school.management.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.school.management.model.api.response.ChildFee;
import pl.school.management.model.api.response.SettlementResponse;
import pl.school.management.model.entity.Attendance;
import pl.school.management.model.entity.Parent;
import pl.school.management.repository.AttendanceRepository;
import pl.school.management.repository.ParentRepository;
import pl.school.management.repository.SchoolRepository;
import pl.school.management.service.SettlementService;
import pl.school.management.util.PaidHoursCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * If we had association between parent -> children -> attendances it would be much easier to retrieve
 * information about settlements. In that case it would be simple projections and 3 joins to retrieve that data.
 * Due to limitations in domain model, I had to first fetch child attendances, create map with hours and the return response.
 */
@Service
public class SettlementServiceImpl implements SettlementService {

    private final AttendanceRepository attendanceRepository;
    private final ParentRepository parentRepository;
    private final SchoolRepository schoolRepository;

    public SettlementServiceImpl(AttendanceRepository attendanceRepository,
                                 ParentRepository parentRepository,
                                 SchoolRepository schoolRepository) {
        this.attendanceRepository = attendanceRepository;
        this.parentRepository = parentRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<SettlementResponse> getSettlementForSchool(Long schoolId, int month) {
        List<Long> parentIds = schoolRepository.parentIdsForSchool(schoolId);
        List<SettlementResponse> responses = new ArrayList<>(parentIds.size());
        parentIds.forEach(parentId -> responses.add(getSettlementForParent(schoolId, parentId, month)));
        return responses;
    }

    @Override
    public SettlementResponse getSettlementForParent(Long schoolId, Long parentId, int month) {
        Calendar calendar = Calendar.getInstance();
        LocalDateTime dateFrom = LocalDateTime.of(calendar.get(Calendar.YEAR), month, 1, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(calendar.get(Calendar.YEAR), month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59);
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(EntityNotFoundException::new);
        List<Attendance> childrenAttendances = attendanceRepository
                .findAllByChildParentIdAndEntryDateGreaterThanAndExitDateLessThan(parentId, dateFrom, dateTo);
        BigDecimal schoolHourPrice = schoolRepository.findSchoolHoursPrice(schoolId);
        if (childrenAttendances.isEmpty() || Objects.isNull(schoolHourPrice)) {
            return new SettlementResponse();
        }

        Map<Long, ChildFee> childFeeMap = computeChildFeeMap(childrenAttendances, schoolHourPrice);

        float totalFee = (float) childFeeMap.values()
                .stream()
                .mapToDouble(ChildFee::getTotalAmount)
                .sum();

        SettlementResponse settlementResponse = new SettlementResponse();
        settlementResponse.setParentFirstName(parent.getFirstName());
        settlementResponse.setParentLastName(parent.getLastName());
        settlementResponse.setTotalFees(totalFee);
        settlementResponse.setFeeList(childFeeMap.values().stream().toList());

        return settlementResponse;
    }

    private Map<Long, ChildFee> computeChildFeeMap(List<Attendance> childrenAttendances, BigDecimal schoolHourPrice) {
        Map<Long, ChildFee> childFeeMap = new HashMap<>();
        childrenAttendances.forEach(attendance -> {
            int paidHours = PaidHoursCalculator.calculate(attendance.getEntryDate(), attendance.getExitDate());
            int totalHours = (int) ChronoUnit.HOURS.between(attendance.getEntryDate(), attendance.getExitDate());
            childFeeMap.compute(attendance.getChild().getId(), (key, val) -> {
                if (Objects.isNull(val)) {
                    return new ChildFee(attendance.getChild().getId(),
                            attendance.getChild().getFirstName(),
                            paidHours * schoolHourPrice.floatValue(),
                            totalHours);
                }

                val.incrementTotalHours(totalHours);
                val.incrementTotalAmount(paidHours * schoolHourPrice.floatValue());
                return val;
            });
        });
        return childFeeMap;
    }

}
