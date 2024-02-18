package pl.school.management.repository;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.school.management.model.entity.School;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@ActiveProfiles("testcontainer")
public class SchoolRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("user")
            .withPassword("pass")
            .withExposedPorts(5432)
            .withInitScript("db/migration/V1__init_tables.sql")
            .withDatabaseName("school-db");

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    void testSave_expectSuccess() {
        //given
        double hourPrice = 205.3485;
        School school = new School();
        school.setName("test");
        school.setHourPrice(BigDecimal.valueOf(hourPrice));

        School savedSchool = schoolRepository.save(school);

        //when
        Optional<School> resultSchool = schoolRepository.findById(savedSchool.getId());

        //then
        assertThat(resultSchool)
                .isNotEmpty();
        assertThat(resultSchool.get().getHourPrice())
                .isEqualTo(BigDecimal.valueOf(205.35));
    }

    @Test
    void testSave_expectFail() {
        //given
        School school = new School();
        school.setName(null);
        school.setHourPrice(BigDecimal.valueOf(205.5555));

        //when //then
        assertThrows(DataIntegrityViolationException.class,
                () -> schoolRepository.save(school));
    }

}