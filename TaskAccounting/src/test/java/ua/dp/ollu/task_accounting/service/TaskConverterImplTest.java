package ua.dp.ollu.task_accounting.service;

import org.junit.Test;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.service.TaskConverter.TaskConverterImpl;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskConverterImplTest {

    private String expected = "2019-04-12";
    private TaskConverterImpl converter = new TaskConverterImpl();
    private Person[] persons = {
            newPerson(0, "Bill"),
            newPerson(1, "Still"),
            newPerson(2, "Jone"),
            newPerson(3, "Andrea"),
            newPerson(4, "Julia")
    };

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
            add(newPersonsInTask(0));
            add(newPersonsInTask(1));
            add(newPersonsInTask(2));
        }};

        ArrayList<Person> peoples = new ArrayList<Person>() {{
            add(persons[1]);
            add(persons[2]);
            add(persons[3]);
        }};
        Task task = new Task();
        task.setId(105L);
        task.setPersons(source);
        Set<PersonsInTask> inTaskList = converter.syncPersonsInTaskListFromPersonsList(task, peoples);
        assertEquals(3, inTaskList.size());
        assertTrue(inTaskList.stream().anyMatch(inTask -> inTask.getPerson().getId() == 1));
        assertTrue(inTaskList.stream().anyMatch(inTask -> inTask.getPerson().getId() == 2));
        assertTrue(inTaskList.stream().anyMatch(inTask -> inTask.getPerson().getId() == 3));
    }

    private Person newPerson(long id, String name) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }

    private PersonsInTask newPersonsInTask(int personId) {
        PersonsInTask personInTask = new PersonsInTask();
        personInTask.setId(0);
        personInTask.setPerson(persons[personId]);
        return personInTask;
    }
}
