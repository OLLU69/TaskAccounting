package ua.dp.ollu.task_accounting.service;

import lombok.Getter;
import lombok.Setter;
import ua.dp.ollu.task_accounting.model.Person;

import java.util.List;

@Getter
@Setter
public class WebTask {
    private List<Person> personsList;
    private Long id;
    private String name;// название задачи
    private String startDate; //дата начала выполнения(без времени, с точностью до дня)
    private String endDate;// дата окончания выполнения (без времени, с точностью до дня)
}
