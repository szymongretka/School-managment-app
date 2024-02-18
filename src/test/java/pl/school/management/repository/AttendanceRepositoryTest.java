package pl.school.management.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.school.management.model.entity.Attendance;
import pl.school.management.model.entity.Child;
import pl.school.management.model.entity.Parent;
import pl.school.management.model.entity.School;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AttendanceRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    void testAttendanceSave_expectSaved() {
        //given
        String lastName = "LastName";
        School school = initSchool("Szkola 1");
        Parent parent = initParent(lastName);
        Attendance attendance = new Attendance();
        attendance.setChild(initChild(school, parent, lastName));
        attendance.setEntryDate(LocalDateTime.now().minusSeconds(60));
        attendance.setExitDate(LocalDateTime.now());

        //when
        Attendance savedAttendance = attendanceRepository.save(attendance);

        //then
        assertThat(savedAttendance)
                .isNotNull();
    }

    @Test
    void testFindAllByChildParentId_expectCorrectAttendances() {
        //given
        LocalDateTime from = LocalDateTime.now().minusSeconds(180);
        LocalDateTime to = LocalDateTime.now().plusSeconds(180);

        String lastName1 = "LastName1";
        String lastName2 = "LastName2";

        School school = initSchool("Szkola 1");
        Parent parent1 = initParent(lastName1);
        Parent parent2 = initParent(lastName2);
        Child child1 = initChild(school, parent1, lastName1);
        Child child2 = initChild(school, parent2, lastName2);

        Attendance attendance1 = new Attendance();
        attendance1.setChild(child1);
        attendance1.setEntryDate(LocalDateTime.now().minusSeconds(60));
        attendance1.setExitDate(LocalDateTime.now());
        attendanceRepository.save(attendance1);

        Attendance attendance2 = new Attendance();
        attendance2.setChild(child2);
        attendance2.setEntryDate(LocalDateTime.now().minusSeconds(120));
        attendance2.setExitDate(LocalDateTime.now());
        attendanceRepository.save(attendance2);

        Attendance attendance3 = new Attendance();
        attendance3.setChild(child1);
        attendance3.setEntryDate(LocalDateTime.now().minusSeconds(6000));
        attendance3.setExitDate(LocalDateTime.now().minusSeconds(5000));
        attendanceRepository.save(attendance3);

        Long savedParentId = parent1.getId();

        //when
        List<Attendance> result = attendanceRepository
                .findAllByChildParentIdAndEntryDateGreaterThanAndExitDateLessThan(savedParentId, from, to);

        //then
        assertThat(result)
                .hasSize(1);
        assertThat(result.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(attendance1);
    }

    @Test
    void testFindAllByChildSchoolId_expectCorrectAttendances() {
        //given
        LocalDateTime from = LocalDateTime.now().minusSeconds(180);
        LocalDateTime to = LocalDateTime.now().plusSeconds(180);

        String lastName = "LastName1";
        String schoolName1 = "Szkola 1";
        String schoolName2 = "Szkola 2";
        School school1 = initSchool(schoolName1);
        School school2 = initSchool(schoolName2);
        Parent parent = initParent(lastName);
        Child child1 = initChild(school1, parent, lastName);
        Child child2 = initChild(school2, parent, lastName);

        Attendance attendance1 = new Attendance();
        attendance1.setChild(child1);
        attendance1.setEntryDate(LocalDateTime.now().minusSeconds(60));
        attendance1.setExitDate(LocalDateTime.now());
        attendanceRepository.save(attendance1);

        Attendance attendance2 = new Attendance();
        attendance2.setChild(child2);
        attendance2.setEntryDate(LocalDateTime.now().minusSeconds(120));
        attendance2.setExitDate(LocalDateTime.now());
        attendanceRepository.save(attendance2);

        Long schoolId = school2.getId();

        //when
        List<Attendance> result = attendanceRepository
                .findAllByChildSchoolIdAndEntryDateGreaterThanAndExitDateLessThan(schoolId, from, to);

        //then
        assertThat(result)
                .hasSize(1);
        assertThat(result.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(attendance2);
    }

    private Child initChild(School school, Parent parent, String lastName) {
        Child child = new Child();
        child.setFirstName("Dziecko");
        child.setLastName(lastName);
        child.setParent(parent);
        child.setSchool(school);
        return childRepository.save(child);
    }

    private School initSchool(String schoolName) {
        School school = new School();
        school.setName(schoolName);
        school.setHourPrice(BigDecimal.valueOf(1234));
        return schoolRepository.save(school);
    }

    private Parent initParent(String lastName) {
        Parent parent = new Parent();
        parent.setFirstName("Rodzic");
        parent.setLastName(lastName);
        return parentRepository.save(parent);
    }

}