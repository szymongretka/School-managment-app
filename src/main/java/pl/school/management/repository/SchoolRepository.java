package pl.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.school.management.model.entity.School;

import java.math.BigDecimal;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("SELECT s.hourPrice FROM School s WHERE s.id = :schoolId")
    BigDecimal findSchoolHoursPrice(Long schoolId);

}
