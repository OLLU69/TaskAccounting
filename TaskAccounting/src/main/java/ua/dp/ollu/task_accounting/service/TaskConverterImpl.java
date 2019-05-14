package ua.dp.ollu.task_accounting.service;

import org.springframework.stereotype.Service;
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
    public void syncTaskPersons(Task task, Task webTask) {
        syncPersonsInTaskListFromPersonsList(task, webTask.getPersons());
    }

    //убирает из исходной task тех людей что нет в peoples.
    //добавляет те что есть в peoples
    //остальные оставляет как есть
    Set<PersonsInTask> syncPersonsInTaskListFromPersonsList(Task task, Set<PersonsInTask> peoples) {
        Set<PersonsInTask> source = task.getPersons();
        //отбираем что есть и добавляет новые
        List<PersonsInTask> inTasks = peoples.stream().map(person -> source.stream()
                .filter(tsk -> tsk.getPerson().getId().equals(person.getId()))
                .findFirst().orElse(new PersonsInTask(task, person.getPerson()))).collect(Collectors.toList());
        source.clear();
        source.addAll(inTasks);
        return source;
    }
}
