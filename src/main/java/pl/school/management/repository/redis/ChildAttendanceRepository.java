package pl.school.management.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.school.management.model.redis.ChildAttendance;

@Repository
public interface ChildAttendanceRepository extends CrudRepository<ChildAttendance, Long> {
}
