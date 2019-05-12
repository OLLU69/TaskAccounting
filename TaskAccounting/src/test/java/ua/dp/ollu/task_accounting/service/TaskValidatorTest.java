package ua.dp.ollu.task_accounting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.Task;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskValidatorTest {

    @Autowired
    TaskService taskService;
    @Autowired
    TaskValidator validator;
    @Autowired
    TaskConverter converter;

    @Test
    public void validateDateTest() {
        Task task = new Task();
        task.setStartDate(converter.parse("2019-05-12"));
        task.setEndDate(converter.parse("2019-05-13"));
        assertTrue(validator.validateDate(task));
        task.setEndDate(converter.parse("2019-05-12"));
        assertTrue(validator.validateDate(task));
        task.setEndDate(converter.parse("2019-05-11"));
        assertFalse(validator.validateDate(task));
    }

    @Test()
    public void peopleBusyValidate() {
        WebTask webTask = new WebTask();
        setupTask(webTask, "task1", "2019-05-12", "2019-05-14");
        List<Person> allPersons = taskService.getPersons();
        webTask.setPersonsList(allPersons.subList(0, 1));
        assertEquals(0, validator.peopleBusyValidate(converter.newTask(webTask)).size());
        taskService.save(webTask);
        setupTask(webTask, "task2", "2019-05-15", "2019-05-16");
        webTask = taskService.save(webTask);

        webTask.setPersonsList(allPersons.subList(0, 2));
        setupTask(webTask, "task3", "2019-05-15", "2019-05-16");
        taskService.update(webTask.getId(), webTask);


        boolean passed = false;
        try {
            setupTask(webTask, "task3", "2019-05-13", "2019-05-16");
            taskService.save(webTask);
        } catch (Error er) {
            passed = true;
        }
        assertTrue(passed);

    }

    private void setupTask(WebTask task, String name, String startDate, String endDate) {
        task.setName(name);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
    }
}
