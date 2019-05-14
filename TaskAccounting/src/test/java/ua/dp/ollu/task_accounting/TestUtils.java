package ua.dp.ollu.task_accounting;

import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.service.TaskConverterImpl;

import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {
    public static TaskConverterImpl converter = new TaskConverterImpl();
    private static Person[] persons = {
            newPerson(0, "Bill"),
            newPerson(1, "Still"),
            newPerson(2, "Jone"),
            newPerson(3, "Andrea"),
            newPerson(4, "Julia")
    };


    public static void setupTaskDate(Task task, String name, String startDate, String endDate) {
        task.setName(name);
        task.setStartDate(converter.parse(startDate));
        task.setEndDate(converter.parse(endDate));
    }

    public static void setupTaskPersons(Task task, List<Person> personList) {
        task.setPersons(personList.stream().map(person -> new PersonsInTask(task, person)).collect(Collectors.toSet()));
    }

    public static PersonsInTask newPersonsInTask(int id, Task task, int personId) {
        PersonsInTask personsInTask = newPersonsInTask(id, personId);
        personsInTask.setTask(task);
        return personsInTask;
    }

    public static PersonsInTask newPersonsInTask(int id, int personId) {
        PersonsInTask personInTask = new PersonsInTask();
        personInTask.setId(id);
        personInTask.setPerson(persons[personId]);
        return personInTask;
    }

    public static Task newTask(String name, String startDate, String endDate) {
        return new Task(
                name,
                converter.parse(startDate),
                converter.parse(endDate));
    }

    private static Person newPerson(long id, String name) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }
}
