package pl.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.school.management.model.entity.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
