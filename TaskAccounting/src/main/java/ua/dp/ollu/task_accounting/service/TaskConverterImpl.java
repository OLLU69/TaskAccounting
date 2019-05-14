package ua.dp.ollu.task_accounting.service;

import org.springframework.stereotype.Service;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("dateConverter")
public
class TaskConverterImpl implements TaskConverter {
    private static final String pattern = "yyyy-MM-dd";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    @Override
    public Date parse(String string) {
        try {
            return formatter.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    @Override
    public String format(Date date) {
        return formatter.format(date);
    }

    @Override
    public Task newTask(WebTask webTask) {
        Task task = new Task();
        task.setId(webTask.getId());
        task.setName(webTask.getName());
        task.setStartDate(parse(webTask.getStartDate()));
        task.setEndDate(parse(webTask.getEndDate()));
        task.setPersons(webTask.getPersonsList()
                .stream()
                .map(person -> new PersonsInTask(task, person))
                .collect(Collectors.toSet()));

        return task;
    }

    @Override
    public WebTask newWebTask(Task task) {
        return new WebTask() {{
            setId(task.getId());
            setName(task.getName());
            setStartDate(format(task.getStartDate()));
            setEndDate(format(task.getEndDate()));
            setPersonsList(task.getPersons().stream().map(PersonsInTask::getPerson).collect(Collectors.toList()));
        }};
    }

    @Override
    public void webTaskToTask(WebTask webTask, Task task) {
        if (task == null) return;
        if (webTask.getName() != null)
            task.setName(webTask.getName());
        if (webTask.getStartDate() != null)
            task.setStartDate(parse(webTask.getStartDate()));
        if (webTask.getEndDate() != null)
            task.setEndDate(parse(webTask.getEndDate()));
        task.setPersons(syncPersonsInTaskListFromPersonsList(task, webTask.getPersonsList()));
    }

    //убирает из исходной task тех людей что нет в peoples.
    //добавляет те что есть в peoples
    //остальные оставляет как есть
    Set<PersonsInTask> syncPersonsInTaskListFromPersonsList(Task task, List<Person> peoples) {
        Set<PersonsInTask> source = task.getPersons();
        //отбираем что есть и добавляет новые
        List<PersonsInTask> inTasks = peoples.stream().map(person -> source.stream()
                .filter(tsk -> tsk.getPerson().getId().equals(person.getId()))
                .findFirst().orElse(new PersonsInTask(task, person))).collect(Collectors.toList());
        source.addAll(inTasks);
        return source;
    }
}
