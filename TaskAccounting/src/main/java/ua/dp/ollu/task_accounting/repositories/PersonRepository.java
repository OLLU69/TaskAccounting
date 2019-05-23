package ua.dp.ollu.task_accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ollu.task_accounting.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
