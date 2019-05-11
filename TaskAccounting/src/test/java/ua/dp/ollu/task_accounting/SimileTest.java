package ua.dp.ollu.task_accounting;

import org.junit.Assert;
import org.junit.Test;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.service.DateConverter;
import ua.dp.ollu.task_accounting.service.TaskServiceImpl;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SimileTest {
    private Person[] persons = {
            newPerson(0, "Bill"),
            newPerson(1, "Still"),
            newPerson(2, "Jone"),
            newPerson(3, "Andrea"),
            newPerson(4, "Julia")
    };

    @Test
    public void conversionTest() {
        DateConverter converter = new DateConverter.TaskConverter();
        String expected = "2019-04-12";
        Date date = converter.parse(expected);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2019, calendar.get(Calendar.YEAR));
        assertEquals(4, calendar.get(Calendar.MONTH) + 1);
        assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(expected, converter.format(date));
    }

    @Test
    public void updateTest() {

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
        Assert.assertNotNull(inTaskList.stream().filter(inTask -> inTask.getPerson().getId() == 1).findFirst().orElse(null));
        Assert.assertNotNull(inTaskList.stream().filter(inTask -> inTask.getPerson().getId() == 2).findFirst().orElse(null));
        Assert.assertNotNull(inTaskList.stream().filter(inTask -> inTask.getPerson().getId() == 3).findFirst().orElse(null));
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

    @Test
    public void equalityTest() {
        DateConverter converter = new DateConverter.TaskConverter();
        Task task = new Task(
                "aa",
                converter.parse("2019-05-01"),
                converter.parse("2019-05-05"));
        task.setId(1L);

        Person people = new Person() {{
            setId(10L);
            setName("Тарас");
        }};
        PersonsInTask inTask1 = new PersonsInTask(task, people);
        PersonsInTask inTask2 = new PersonsInTask(task, people);
        assertEquals(inTask1, inTask2);
        HashSet<PersonsInTask> inTasks = new HashSet<>();
        inTasks.add(inTask1);
        assertEquals(1, inTasks.size());
        inTasks.add(inTask2);
        assertEquals(1, inTasks.size());
        System.out.println("Ok");
    }
}
