package ua.dp.ollu.task_accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity()
@Table(name = "PERSON", schema = "TASK_PLAN")
@Data
@RequiredArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "person", orphanRemoval = true)
    List<PersonsInTask> persons;

    public Person(String name) {

        this.name = name;
    }
}
