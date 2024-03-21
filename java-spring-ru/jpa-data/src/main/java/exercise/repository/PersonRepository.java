package exercise.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import exercise.model.Person;

// BEGIN
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
// END
