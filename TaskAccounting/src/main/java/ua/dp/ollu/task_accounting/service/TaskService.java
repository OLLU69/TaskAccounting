package ua.dp.ollu.task_accounting.service;

import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    Task getTask(Long id) throws Exception;

    Task save(Task webTask);

    Task update(Long id, Task webTask);

    void delete(Long id);

    List<Person> getPersons();

    Person getPerson(Long id);
}


