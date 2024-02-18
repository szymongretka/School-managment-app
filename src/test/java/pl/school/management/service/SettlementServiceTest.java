package pl.school.management.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.school.management.model.api.response.ChildFee;
import pl.school.management.model.api.response.SettlementResponse;
import pl.school.management.model.entity.Attendance;
import pl.school.management.model.entity.Child;
import pl.school.management.model.entity.Parent;
import pl.school.management.model.entity.School;
import pl.school.management.repository.AttendanceRepository;
import pl.school.management.repository.ParentRepository;
import pl.school.management.repository.SchoolRepository;
import pl.school.management.service.impl.SettlementServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class SettlementServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SettlementServiceImpl settlementService;

    @Test
    void getSettlementForSchool() {
        //given
        long schoolId = 2L;
        long parentId = 6L;
        int month = 6;
        String lastName = "Nazwisko";
        String childName1 = "FirstChild";
        String childName2 = "Second";
        Long childId1 = 1L;
        Long childId2 = 2L;

        BigDecimal schoolHourPrice = BigDecimal.valueOf(20.0);

        School school = initSchool(schoolHourPrice);
        Parent parent = initParent(lastName);
        Child child1 = initChild(childId1, school, parent, childName1);
        Child child2 = initChild(childId2, school, parent, childName2);

        Attendance attendance1 = new Attendance();
        attendance1.setChild(child1);
        attendance1.setEntryDate(LocalDateTime.of(2024, month, 2, 7, 30, 0));
        attendance1.setExitDate(LocalDateTime.of(2024, month, 2, 11, 30, 0));

        Attendance attendance2 = new Attendance();
        attendance2.setChild(child1);
        attendance2.setEntryDate(LocalDateTime.of(2024, month, 3, 6, 30, 0));
        attendance2.setExitDate(LocalDateTime.of(2024, month, 3, 15, 30, 0));

        Attendance attendance3 = new Attendance();
        attendance3.setChild(child2);
        attendance3.setEntryDate(LocalDateTime.of(2024, month, 2, 4, 30, 0));
        attendance3.setExitDate(LocalDateTime.of(2024, month, 2, 18, 30, 0));

        List<Attendance> attendances = List.of(attendance1, attendance2, attendance3);

        Mockito.when(schoolRepository.parentIdsForSchool(schoolId))
                .thenReturn(List.of(parentId));
        Mockito.when(attendanceRepository
                        .findAllByChildParentIdAndEntryDateGreaterThanAndExitDateLessThan(any(), any(), any()))
                .thenReturn(attendances);
        Mockito.when(parentRepository.findById(parentId))
                .thenReturn(Optional.of(parent));
        Mockito.when(schoolRepository.findSchoolHoursPrice(schoolId))
                .thenReturn(schoolHourPrice);


        //when
        List<SettlementResponse> settlementResponses = settlementService.getSettlementForSchool(schoolId, month);

        //then
        ChildFee childFee1 = new ChildFee(childId1, childName1, 100.0f, 13);
        ChildFee childFee2 = new ChildFee(childId2, childName2, 200.0f, 14);
        List<ChildFee> childFees = List.of(childFee1, childFee2);

        SettlementResponse expectedResponse = new SettlementResponse();
        expectedResponse.setParentLastName(lastName);
        expectedResponse.setParentFirstName("Rodzic");
        expectedResponse.setTotalFees(300.0f);
        expectedResponse.setFeeList(childFees);
        assertThat(settlementResponses)
                .hasSize(1);
        assertThat(settlementResponses.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void getSettlementForParent_withoutParentInDb_expectException() {
        assertThrows(EntityNotFoundException.class,
                () -> settlementService.getSettlementForParent(1L, 2L, 3));
    }

    @Test
    void getSettlementForParent_expectEmptyReponse() {
        //given
        Mockito.when(parentRepository.findById(any()))
                .thenReturn(Optional.of(new Parent()));
        Mockito.when(attendanceRepository
                        .findAllByChildParentIdAndEntryDateGreaterThanAndExitDateLessThan(any(), any(), any()))
                .thenReturn(Collections.emptyList());
        Mockito.when(schoolRepository.findSchoolHoursPrice(any()))
                .thenReturn(BigDecimal.ONE);

        //when
        SettlementResponse settlementResponse = settlementService.getSettlementForParent(1L, 1L, 1);

        //then
        assertThat(settlementResponse)
                .usingRecursiveComparison()
                .isEqualTo(new SettlementResponse());
    }

    @Test
    void getSettlementForParent_expectFullReport() {
        //given
        long schoolId = 2L;
        long parentId = 6L;
        int month = 6;
        String lastName = "Nazwisko";
        String childName1 = "FirstChild";
        String childName2 = "Second";
        Long childId1 = 1L;
        Long childId2 = 2L;

        BigDecimal schoolHourPrice = BigDecimal.valueOf(20.0);

        School school = initSchool(schoolHourPrice);
        Parent parent = initParent(lastName);
        Child child1 = initChild(childId1, school, parent, childName1);
        Child child2 = initChild(childId2, school, parent, childName2);

        Attendance attendance1 = new Attendance();
        attendance1.setChild(child1);
        attendance1.setEntryDate(LocalDateTime.of(2024, month, 2, 7, 30, 0));
        attendance1.setExitDate(LocalDateTime.of(2024, month, 2, 11, 30, 0));

        Attendance attendance2 = new Attendance();
        attendance2.setChild(child1);
        attendance2.setEntryDate(LocalDateTime.of(2024, month, 3, 6, 30, 0));
        attendance2.setExitDate(LocalDateTime.of(2024, month, 3, 15, 30, 0));

        Attendance attendance3 = new Attendance();
        attendance3.setChild(child2);
        attendance3.setEntryDate(LocalDateTime.of(2024, month, 2, 4, 30, 0));
        attendance3.setExitDate(LocalDateTime.of(2024, month, 2, 18, 30, 0));

        List<Attendance> attendances = List.of(attendance1, attendance2, attendance3);

        Mockito.when(attendanceRepository
                        .findAllByChildParentIdAndEntryDateGreaterThanAndExitDateLessThan(any(), any(), any()))
                .thenReturn(attendances);
        Mockito.when(parentRepository.findById(parentId))
                .thenReturn(Optional.of(parent));
        Mockito.when(schoolRepository.findSchoolHoursPrice(schoolId))
                .thenReturn(schoolHourPrice);


        //when
        SettlementResponse settlementResponse = settlementService.getSettlementForParent(schoolId, parentId, month);

        //then
        ChildFee childFee1 = new ChildFee(childId1, childName1, 100.0f, 13);
        ChildFee childFee2 = new ChildFee(childId2, childName2, 200.0f, 14);
        List<ChildFee> childFees = List.of(childFee1, childFee2);

        SettlementResponse expectedResponse = new SettlementResponse();
        expectedResponse.setParentLastName(lastName);
        expectedResponse.setParentFirstName("Rodzic");
        expectedResponse.setTotalFees(300.0f);
        expectedResponse.setFeeList(childFees);
        assertThat(settlementResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    private School initSchool(BigDecimal hourPrice) {
        School school = new School();
        school.setName("Szkola 1");
        school.setHourPrice(hourPrice);
        return school;
    }

    private Parent initParent(String lastName) {
        Parent parent = new Parent();
        parent.setFirstName("Rodzic");
        parent.setLastName(lastName);
        return parent;
    }

    private Child initChild(Long id, School school, Parent parent, String firstName) {
        Child child = new Child();
        child.setId(id);
        child.setSchool(school);
        child.setParent(parent);
        child.setLastName("Nazwisko");
        child.setFirstName(firstName);
        return child;
    }

}