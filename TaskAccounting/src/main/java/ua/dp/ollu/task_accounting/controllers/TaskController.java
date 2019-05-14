package ua.dp.ollu.task_accounting.controllers;

import org.springframework.web.bind.annotation.*;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.service.TaskService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping(value = "/tasks/{id}")
    public Task getTask(@PathVariable Long id) throws Exception {
        return taskService.getTask(id);
    }

    @PostMapping(value = "/tasks")
    public Task save(@RequestBody Task webTask) {
        return taskService.save(webTask);
    }

    @PutMapping(value = "/tasks/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task webTask) {
        return taskService.update(id, webTask);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping(value = "/persons")
    public List<Person> getPersons() {
        return taskService.getPersons();
    }

    @GetMapping(value = "/persons/{id}")
    public Person getPerson(@PathVariable Long id) {
        return taskService.getPerson(id);
    }
}
