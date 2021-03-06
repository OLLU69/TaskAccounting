package ua.dp.ollu.task_accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity()
@Table(name = "PERSONS_IN_TASK", schema = "TASK_PLAN")
@Data
@RequiredArgsConstructor
public class PersonsInTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "TASKS_ID")
    private Task task;

    @ManyToOne()
    @JoinColumn(name = "PERSONS_ID")
    private Person person;

    public PersonsInTask(Task task, Person person) {
        this.task = task;
        this.person = person;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PersonsInTask)) return false;
        PersonsInTask inTask = (PersonsInTask) obj;
        return this.task.getId().equals(inTask.task.getId()) && person.getId().equals(inTask.person.getId());
    }

    @Override
    public int hashCode() {
        int idHash = 0, personIdHash = 0;
        try {
            idHash = task.getId().hashCode();
        } catch (Throwable ignored) {
        }
        try {
            personIdHash = person.getId().hashCode();
        } catch (Throwable ignored) {
        }
        return idHash + personIdHash;
    }
}
