package ua.dp.ollu.task_accounting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity()
@Table(name = "PERSON", schema = "TASK_PLAN")
@Getter
@Setter
@JsonIgnoreProperties("persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    List<PersonsInTask> persons;

    public Person() {

    }

    public Person(String name) {

        this.name = name;
    }
}
