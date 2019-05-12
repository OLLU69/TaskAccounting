package ua.dp.ollu.task_accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dp.ollu.task_accounting.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
