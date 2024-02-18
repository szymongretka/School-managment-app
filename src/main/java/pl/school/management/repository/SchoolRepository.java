package pl.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.school.management.model.entity.School;

import java.math.BigDecimal;
import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("SELECT s.hourPrice FROM School s WHERE s.id = :schoolId")
    BigDecimal findSchoolHoursPrice(Long schoolId);

    @Query("""
            SELECT DISTINCT p.id
            FROM School s
                JOIN Child c on c.school.id = s.id
                JOIN Parent p on p.id = c.parent.id
            WHERE s.id = :schoolId
            """)
    List<Long> parentIdsForSchool(Long schoolId);

}
