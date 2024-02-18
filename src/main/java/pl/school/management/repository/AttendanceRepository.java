package pl.school.management.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.school.management.model.entity.Attendance;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Transactional(readOnly = true)
    @EntityGraph(attributePaths = {"child", "child.parent"})
    List<Attendance> findAllByChildParentIdAndEntryDateGreaterThanAndExitDateLessThan(Long parentId, LocalDateTime from, LocalDateTime to);

    @Transactional(readOnly = true)
    @EntityGraph(attributePaths = {"child", "child.parent"})
    List<Attendance> findAllByChildSchoolIdAndEntryDateGreaterThanAndExitDateLessThan(Long parentId, LocalDateTime from, LocalDateTime to);

}
