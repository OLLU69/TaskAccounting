package ua.dp.ollu.task_accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.dp.ollu.task_accounting.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    Optional<Task> findById(Long id);

    @Override
    void delete(Task task);

    @Query("delete from  Task t where t.name = :name")
    @Modifying
    void deleteByName(@Param("name") String name);

    @Query("select t from  Task t where t.name = :name")
    List<Task> findByName(String name);
}
