package ua.dp.ollu.task_accounting.service;

import ua.dp.ollu.task_accounting.model.Person;

import java.util.List;

public interface TaskService {
    List<WebTask> getAllTasks();

    WebTask getTask(Long id) throws Exception;

    WebTask save(WebTask webTask);

    WebTask update(Long id, WebTask webTask);

    void delete(Long id);

    List<Person> getPersons();

    Person getPerson(Long id);
}


