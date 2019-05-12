package ua.dp.ollu.task_accounting.service;

import org.junit.Test;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskServiceImplTest {

    private Person[] persons = {
            newPerson(0, "Bill"),
            newPerson(1, "Still"),
            newPerson(2, "Jone"),
            newPerson(3, "Andrea"),
            newPerson(4, "Julia")
    };

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
