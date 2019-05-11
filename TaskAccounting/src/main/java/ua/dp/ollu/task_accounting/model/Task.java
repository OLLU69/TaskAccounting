package ua.dp.ollu.task_accounting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity()
@Table(name = "TASK", schema = "TASK_PLAN")
@Getter
@Setter
public class Task {
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<PersonsInTask> persons;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;// название задачи
    @Column(name = "startDate")
    private Date startDate; //дата начала выполнения(без времени, с точностью до дня)
    @Column(name = "endDate")
    private Date endDate;// дата окончания выполнения (без времени, с точностью до дня)

    public Task() {
    }

    Task(String name, Date startDate, Date endDate) {

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
