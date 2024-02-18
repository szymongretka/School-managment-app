package pl.school.management.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.school.management.model.dto.AttendanceDto;
import pl.school.management.model.entity.Attendance;
import pl.school.management.model.redis.ChildAttendance;
import pl.school.management.repository.AttendanceRepository;
import pl.school.management.repository.ChildRepository;
import pl.school.management.repository.redis.ChildAttendanceRepository;
import pl.school.management.service.AttendanceService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private final ChildAttendanceRepository redisAttendanceRepository;
    private final AttendanceRepository attendanceRepository;
    private final ChildRepository childRepository;

    public AttendanceServiceImpl(ChildAttendanceRepository redisAttendanceRepository,
                                 AttendanceRepository attendanceRepository,
                                 ChildRepository childRepository) {
        this.redisAttendanceRepository = redisAttendanceRepository;
        this.attendanceRepository = attendanceRepository;
        this.childRepository = childRepository;
    }

    @Override
    public LocalDateTime recordEntrance(Long childId) {
        LocalDateTime now = LocalDateTime.now();
        logger.info("Recorded entrance of child: {} with local date time: {}", childId, now);
        redisAttendanceRepository.save(new ChildAttendance(childId, now));
        return now;
    }

    @Override
    public LocalDateTime recordExit(Long childId) {
        LocalDateTime now = LocalDateTime.now();
        logger.info("Recorded exit of child: {} with local date time: {}", childId, now);
        Optional<ChildAttendance> childAttendanceOpt = redisAttendanceRepository.findById(childId);
        childAttendanceOpt.ifPresentOrElse(
                childAttendance -> {
                    Attendance attendance =
                            new Attendance(childRepository.getReferenceById(childId), childAttendance.getEntryDate(), now);
                    attendanceRepository.save(attendance);
                    redisAttendanceRepository.delete(childAttendance);
                },
                () -> {
                    logger.error("Child with id: {} was not found in Redis!", childId);
                    throw new EntityNotFoundException();
                });
        return now;
    }

    @Override
    public AttendanceDto addAttendance(AttendanceDto attendanceDto) {
        Attendance attendance =
                new Attendance(
                        childRepository.getReferenceById(attendanceDto.getChildId()),
                        attendanceDto.getDateRange().getEntryDate(),
                        attendanceDto.getDateRange().getExitDate()
                );
        attendance = attendanceRepository.save(attendance);
        return new AttendanceDto().fromEntity(attendance);
    }

}
