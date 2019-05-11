package ua.dp.ollu.task_accounting.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.repositories.PersonRepository;
import ua.dp.ollu.task_accounting.repositories.TaskRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.dp.ollu.task_accounting.service.TaskValidator.validate;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Getter
    private final DateConverter dateConverter;
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final Sort sort = new Sort(Sort.Direction.ASC, "startDate");

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, PersonRepository personRepository, DateConverter dateConverter) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
        this.dateConverter = dateConverter;
    }

    //убирает из исходной task тех людей что нет в peoples.
    //добавляет те что есть в peoples
    //остальные оставляет как есть
    static Set<PersonsInTask> syncPersonsInTaskListFromPersonsList(Task task, List<Person> peoples) {
        Set<PersonsInTask> source = task.getPersons();
        //отбираем что есть и добавляет новые
        List<PersonsInTask> inTasks = peoples.stream().map(person -> {
            PersonsInTask inTask = source.stream()
                    .filter(tsk -> tsk.getPerson().getId().equals(person.getId()))
                    .findFirst().orElse(null);
            if (inTask == null) {
                inTask = new PersonsInTask();
                inTask.setTask(task);
                inTask.setPerson(person);
            }
            return inTask;
        }).collect(Collectors.toList());

        source.clear();
        source.addAll(inTasks);
        return source;
    }

    @Override
    public List<WebTask> getAllTasks() {
        return taskRepository.findAll(sort).stream().map(this::newWebTask).collect(Collectors.toList());
    }

    @Override
    public WebTask getTask(Long id) throws Exception {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) throw new Exception("null value error");
        return newWebTask(task);

    }

    @Override
    public WebTask save(WebTask webTask) {
        Task task = taskRepository.save(validate(newTask(webTask)));
        return newWebTask(task);
    }

    @Override
    public WebTask update(Long id, WebTask webTask) {
        System.out.println(webTask.getName());
        Task task = getTaskForUpdateFromWebTask(id, webTask);
        if (task == null) return null;
        task = taskRepository.save(validate(task));
        return newWebTask(task);

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

    private WebTask newWebTask(Task task) {
        return new WebTask() {{
            setId(task.getId());
            setName(task.getName());
            setStartDate(dateConverter.format(task.getStartDate()));
            setEndDate(dateConverter.format(task.getEndDate()));
            setPersonsList(task.getPersons().stream().map(PersonsInTask::getPerson).collect(Collectors.toList()));
        }};
    }

    private Task newTask(WebTask webTask) {
        Task task = new Task();
        task.setId(webTask.getId());
        task.setName(webTask.getName());
        task.setStartDate(dateConverter.parse(webTask.getStartDate()));
        task.setEndDate(dateConverter.parse(webTask.getEndDate()));
        task.setPersons(webTask.getPersonsList()
                .stream()
                .map(person -> new PersonsInTask(task, person))
                .collect(Collectors.toSet()));

        return task;
    }

    private Task getTaskForUpdateFromWebTask(Long id, WebTask webTask) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return null;
        if (webTask.getName() != null)
            task.setName(webTask.getName());
        if (webTask.getStartDate() != null)
            task.setStartDate(dateConverter.parse(webTask.getStartDate()));
        if (webTask.getEndDate() != null)
            task.setEndDate(dateConverter.parse(webTask.getEndDate()));
        task.setPersons(syncPersonsInTaskListFromPersonsList(task, webTask.getPersonsList()));
        return task;
    }
}
