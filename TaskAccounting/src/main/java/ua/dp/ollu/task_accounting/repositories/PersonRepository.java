package ua.dp.ollu.task_accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.dp.ollu.task_accounting.model.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Override
    Optional<Person> findById(Long id);

    @Query("select p from Person p where p.name = :name")
    Person findByName(String name);
}
