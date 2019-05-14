package ua.dp.ollu.task_accounting.service;

import ua.dp.ollu.task_accounting.model.Task;

import java.util.Date;

public interface TaskConverter {

    void webTaskToTask(WebTask webTask, Task task);

    Date parse(String string);

    String format(Date date);

    Task newTask(WebTask webTask);

    WebTask newWebTask(Task task);
}
