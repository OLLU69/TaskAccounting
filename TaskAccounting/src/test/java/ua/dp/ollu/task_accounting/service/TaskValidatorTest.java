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
import static ua.dp.ollu.task_accounting.TestUtils.setupTaskDate;
import static ua.dp.ollu.task_accounting.TestUtils.setupTaskPersons;

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
        Task task = new Task();
        setupTaskDate(task, "task1", "2019-05-12", "2019-05-14");
        List<Person> allPersons = taskService.getPersons();
        setupTaskPersons(task, allPersons.subList(0, 1));
        assertEquals(0, validator.peopleBusyValidate(task).size());
        taskService.save(task);
        setupTaskDate(task, "task2", "2019-05-15", "2019-05-16");
        task = taskService.save(task);

        setupTaskPersons(task, allPersons.subList(0, 2));
        setupTaskDate(task, "task3", "2019-05-15", "2019-05-16");
        taskService.update(task.getId(), task);


        boolean passed = false;
        try {
            setupTaskDate(task, "task3", "2019-05-13", "2019-05-16");
            task.setId(null);
            taskService.save(task);
        } catch (Error er) {
            passed = true;
        }
        assertTrue(passed);

    }
}
