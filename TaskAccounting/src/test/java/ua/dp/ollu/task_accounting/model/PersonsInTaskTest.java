package ua.dp.ollu.task_accounting.model;

import org.junit.Before;
import org.junit.Test;
import ua.dp.ollu.task_accounting.service.TaskConverter;
import ua.dp.ollu.task_accounting.service.TaskConverterImpl;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class PersonsInTaskTest {

    private TaskConverter converter = new TaskConverterImpl();
    private PersonsInTask inTask1;
    private PersonsInTask inTask2;

    @Before
    public void setUp() {
        Task task = new Task(
                "aa",
                converter.parse("2019-05-01"),
                converter.parse("2019-05-05"));
        task.setId(1L);

        Person people = new Person() {{
            setId(10L);
            setName("Тарас");
        }};
        inTask1 = new PersonsInTask(task, people);
        inTask2 = new PersonsInTask(task, people);
    }

    @Test
    public void equalsTest() {
        assertEquals(inTask1, inTask2);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(inTask1.hashCode(), inTask2.hashCode());
    }

    @Test
    public void hashSetUniquenessTest() {
        assertEquals(inTask1, inTask2);
        HashSet<PersonsInTask> inTasks = new HashSet<>();
        inTasks.add(inTask1);
        assertEquals(1, inTasks.size());
        inTasks.add(inTask2);
        assertEquals(1, inTasks.size());
        System.out.println("Ok");
    }
}
