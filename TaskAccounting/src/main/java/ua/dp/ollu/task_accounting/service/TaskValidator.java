package ua.dp.ollu.task_accounting.service;

import ua.dp.ollu.task_accounting.model.Task;

class TaskValidator {
    static Task validate(Task task) {
        if (task.getEndDate().before(task.getStartDate())) throw new Error("Дата начала больше даты окончания");
        return task;
    }
}
