package ua.dp.ollu.task_accounting.service;

import ua.dp.ollu.task_accounting.model.Task;

import java.util.Date;

public interface TaskConverter {

    Date parse(String string);

    String format(Date date);

    void syncTaskPersons(Task task, Task webTask);
}
