package ua.dp.ollu.task_accounting.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.repositories.PersonRepository;
import ua.dp.ollu.task_accounting.repositories.TaskRepository;

import java.util.Collections;
import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Getter
    private final TaskConverter converter;
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final Sort sort = new Sort(Sort.Direction.ASC, "startDate");

    private TaskValidator validator;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, PersonRepository personRepository, TaskConverter converter, TaskValidator validator) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.converter = converter;
        this.validator = validator;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll(sort);
    }

    @Override
    public Task getTask(Long id) throws Exception {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) throw new Exception("null value error");
        return task;

    }

    @Override
    public Task save(Task task) {
        if (task.getPersons() == null) task.setPersons(Collections.emptySet());
        task.getPersons().forEach(inTask -> inTask.setTask(task));
        return taskRepository.save(validator.validate(task));
    }

    @Override
    public Task update(Long id, Task inTask) {
        if (inTask.getPersons() == null) inTask.setPersons(Collections.emptySet());
        System.out.println(inTask.getName());
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return null;
        converter.syncTaskPersons(task, inTask);
        task = taskRepository.save(validator.validate(task));
        return task;
    }

    @Override
    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        taskRepository.delete(task);
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }
}
