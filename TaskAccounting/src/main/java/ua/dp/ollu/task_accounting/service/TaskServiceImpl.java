package ua.dp.ollu.task_accounting.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.repositories.PersonRepository;
import ua.dp.ollu.task_accounting.repositories.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<WebTask> getAllTasks() {
        return taskRepository.findAll(sort).stream().map(converter::newWebTask).collect(Collectors.toList());
    }

    @Override
    public WebTask getTask(Long id) throws Exception {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) throw new Exception("null value error");
        return converter.newWebTask(task);

    }

    @Override
    public WebTask save(WebTask webTask) {
        Task task = taskRepository.save(validator.validate(converter.newTask(webTask)));
        return converter.newWebTask(task);
    }

    @Override
    public WebTask update(Long id, WebTask webTask) {
        System.out.println(webTask.getName());
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return null;
        converter.webTaskToTask(webTask, task);
        task = taskRepository.save(validator.validate(task));
        return converter.newWebTask(task);

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