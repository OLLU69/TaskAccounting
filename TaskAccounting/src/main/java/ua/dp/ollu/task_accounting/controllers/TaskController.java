package ua.dp.ollu.task_accounting.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.repositories.PersonRepository;
import ua.dp.ollu.task_accounting.repositories.TaskRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TaskController {
    private static final DateConverter formatter = new DateConverter.TaskConverter();
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, PersonRepository personRepository) {
        this.taskRepository = taskRepository;
        this.personRepository = personRepository;
    }

    public static Set<PersonsInTask> syncPersonsInTaskList(Task task, List<Person> peoples) {
        Set<PersonsInTask> source = task.getPersons();
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

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<WebTask> getAllTasks() {
        return taskRepository.findAll().stream().map(WebTask::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}")
    public WebTask getTask(@PathVariable Long id) throws Exception {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) throw new Exception("null value error");
        return new WebTask(task);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public WebTask save(@RequestBody WebTask webTask) {
        Task task = taskRepository.save(webTask.newTask());
        return new WebTask(task);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    public WebTask update(@PathVariable Long id, @RequestBody WebTask webTask) {
        System.out.println(webTask.name);
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return null;
        if (webTask.getName() != null)
            task.setName(webTask.getName());
        if (webTask.getStartDate() != null)
            task.setStartDate(formatter.parse(webTask.getStartDate()));
        if (webTask.getEndDate() != null)
            task.setEndDate(formatter.parse(webTask.getEndDate()));
        webTask.updateTask(task);
        task = taskRepository.save(task);
        return new WebTask(task);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{id}")
    public String delete(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        taskRepository.delete(task);
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/persons/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Getter
    @Setter
    public static class WebTask {
        private List<Person> personsList;
        private long id;
        private String name;// название задачи
        private String startDate; //дата начала выполнения(без времени, с точностью до дня)
        private String endDate;// дата окончания выполнения (без времени, с точностью до дня)

        @SuppressWarnings("unused")
        public WebTask() {
        }

        WebTask(Task task) {
            id = task.getId();
            name = task.getName();
            startDate = formatter.format(task.getStartDate());
            endDate = formatter.format(task.getEndDate());
            personsList = task.getPersons().stream().map(PersonsInTask::getPerson).collect(Collectors.toList());
        }


        private Task newTask() {
            Task task = new Task();
            task.setId(id);
            task.setName(name);
            task.setStartDate(formatter.parse(startDate));
            task.setEndDate(formatter.parse(endDate));
            task.setPersons(personsList.stream().map(person -> new PersonsInTask(task, person)).collect(Collectors.toSet()));
            return task;
        }

        private void updateTask(Task task) {
            task.setPersons(syncPersonsInTaskList(task, personsList));
        }
    }
}
