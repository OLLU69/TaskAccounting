package ua.dp.ollu.task_accounting.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskServiceImplTest {

    private Person[] persons = {
            newPerson(0, "Bill"),
            newPerson(1, "Still"),
            newPerson(2, "Jone"),
            newPerson(3, "Andrea"),
            newPerson(4, "Julia")
    };

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
        Set<PersonsInTask> inTaskList = TaskServiceImpl.syncPersonsInTaskListFromPersonsList(task, peoples);
        assertEquals(3, inTaskList.size());
        assertNotNull(inTaskList.stream().filter(inTask -> inTask.getPerson().getId() == 1).findFirst().orElse(null));
        assertNotNull(inTaskList.stream().filter(inTask -> inTask.getPerson().getId() == 2).findFirst().orElse(null));
        assertNotNull(inTaskList.stream().filter(inTask -> inTask.getPerson().getId() == 3).findFirst().orElse(null));
    }

    @Test(expected = Error.class)
    public void validateTaskTest() {
        Task task = new Task();
        DateConverter converter = new DateConverter.TaskConverter();
        task.setStartDate(converter.parse("2019-05-12"));
        task.setEndDate(converter.parse("2019-05-13"));
        assertEquals(task, TaskValidator.validate(task));
        task.setEndDate(converter.parse("2019-05-12"));
        assertEquals(task, TaskValidator.validate(task));
        task.setEndDate(converter.parse("2019-05-11"));
        TaskValidator.validate(task);
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
