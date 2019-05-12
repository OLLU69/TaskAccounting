package ua.dp.ollu.task_accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ollu.task_accounting.model.PersonsInTask;

import java.util.List;

public interface PersonsInTaskRepository extends JpaRepository<PersonsInTask, Long> {
    List<PersonsInTask> findByPersonId(Long personId);
}
