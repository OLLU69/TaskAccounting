package ua.dp.ollu.task_accounting.service;

import org.junit.Test;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ua.dp.ollu.task_accounting.TestUtils.converter;
import static ua.dp.ollu.task_accounting.TestUtils.newPersonsInTask;

public class TaskConverterImplTest {

    private String expected = "2019-04-12";

    @Test
    public void parse() {
        Date date = converter.parse(expected);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2019, calendar.get(Calendar.YEAR));
        assertEquals(4, calendar.get(Calendar.MONTH) + 1);
        assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void format() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 4 - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        Date date = calendar.getTime();
        assertEquals(expected, converter.format(date));

    }

    @Test
    public void syncPersonsInTaskListFromPersonsListTest() {
        Set<PersonsInTask> source = new HashSet<PersonsInTask>() {{
            add(newPersonsInTask(0, 0));
            add(newPersonsInTask(1, 1));
            add(newPersonsInTask(2, 2));
        }};

        Task task = new Task();
        task.setId(105L);
        Set<PersonsInTask> peoples = new HashSet<PersonsInTask>() {{
            add(newPersonsInTask(1, task, 1));
            add(newPersonsInTask(2, task, 2));
            add(newPersonsInTask(3, task, 3));
        }};
        task.setPersons(source);
        Set<PersonsInTask> inTaskList = converter.syncPersonsInTaskListFromPersonsList(task, peoples);
        assertEquals(3, inTaskList.size());
        assertTrue(inTaskList.stream().anyMatch(inTask -> inTask.getPerson().getId() == 1));
        assertTrue(inTaskList.stream().anyMatch(inTask -> inTask.getPerson().getId() == 2));
        assertTrue(inTaskList.stream().anyMatch(inTask -> inTask.getPerson().getId() == 3));
    }
}
