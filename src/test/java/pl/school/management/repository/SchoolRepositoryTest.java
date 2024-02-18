package pl.school.management.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.school.management.model.entity.Child;
import pl.school.management.model.entity.Parent;
import pl.school.management.model.entity.School;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class SchoolRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Test
    void parentIdsForSchool() {
        //given
        String lastName1 = "LastName1";
        String lastName2 = "LastName2";
        String schoolName1 = "Szkola 1";
        String schoolName2 = "Szkola 2";

        School school1 = initSchool(schoolName1);
        School school2 = initSchool(schoolName2);
        Parent parent1 = initParent(lastName1);
        Parent parent2 = initParent(lastName2);
        Child child1 = initChild(school1, parent1, lastName1);
        Child child12 = initChild(school2, parent2, lastName2);

        //when
        List<Long> ids = schoolRepository.parentIdsForSchool(school2.getId());

        //then
        assertThat(ids)
                .hasSize(1);
        assertThat(ids.getFirst())
                .isEqualTo(parent2.getId());
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