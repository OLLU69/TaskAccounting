package ua.dp.ollu.task_accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.service.TaskService;
import ua.dp.ollu.task_accounting.service.WebTask;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<WebTask> getAllTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}")
    public WebTask getTask(@PathVariable Long id) throws Exception {
        return taskService.getTask(id);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public WebTask save(@RequestBody WebTask webTask) {
        return taskService.save(webTask);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    public WebTask update(@PathVariable Long id, @RequestBody WebTask webTask) {
        return taskService.update(id, webTask);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/persons")
    public List<Person> getPersons() {
        return taskService.getPersons();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/persons/{id}")
    public Person getPerson(@PathVariable Long id) {
        return taskService.getPerson(id);
    }
}
