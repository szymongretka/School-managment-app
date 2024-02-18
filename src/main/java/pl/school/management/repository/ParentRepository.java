package pl.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.school.management.model.entity.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}
