package ua.dp.ollu.task_accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.ollu.task_accounting.model.PersonsInTask;
import ua.dp.ollu.task_accounting.model.Task;
import ua.dp.ollu.task_accounting.repositories.PersonsInTaskRepository;
import ua.dp.ollu.task_accounting.utils.DateUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service("taskValidator")
class TaskValidator {
    private PersonsInTaskRepository inTaskRepository;

    @Autowired
    public TaskValidator(PersonsInTaskRepository inTaskRepository) {
        this.inTaskRepository = inTaskRepository;
    }

    Task validate(Task task) {
        if (!validateDate(task)) throw new Error("Дата начала больше даты окончания");
        List<PersonsInTask> busyPersons = peopleBusyValidate(task);
        if (busyPersons.size() != 0) {
            String busyPersonsNames = busyPersons.stream().map(inTask -> inTask.getPerson().getName()).collect(Collectors.joining(","));
            throw new Error("В данный период есть люди которые уже заняты(" + busyPersonsNames + ")");
        }
        return task;
    }

    boolean validateDate(Task task) {
        return !task.getEndDate().before(task.getStartDate());
    }

    List<PersonsInTask> peopleBusyValidate(Task task) {
        //для каждого человека получить список задач
        return task.getPersons().stream()
                .filter(inTask -> {
                    //получить список задач для этого человека
                    //каждый элемент проверить на пересечение
                    //если есть хотя бы одно пересечение, то пропустить, иначе отбросить
                    return inTaskRepository.findByPersonId(inTask.getPerson().getId()).stream()
                            .anyMatch(inTask1 -> {
                                //если человек принадлежит уже данной задаче, то пропустить (одна та же задача)
                                if (inTask1.getTask().getId().equals(task.getId())) return false;
                                return isIntersection(inTask1.getTask(), inTask.getTask());
                            });
                }).collect(Collectors.toList());
    }

    private boolean isIntersection(Task task1, Task task2) {
        return DateUtils.isIntersection(task1.getStartDate(), task1.getEndDate(), task2.getStartDate(), task2.getEndDate());
    }
}
