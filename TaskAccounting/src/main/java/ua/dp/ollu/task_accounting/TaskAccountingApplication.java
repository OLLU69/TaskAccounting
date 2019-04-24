package ua.dp.ollu.task_accounting;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import ua.dp.ollu.task_accounting.model.Person;
import ua.dp.ollu.task_accounting.repositories.PersonRepository;

import java.util.stream.Stream;

@SpringBootApplication
public class TaskAccountingApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TaskAccountingApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TaskAccountingApplication.class);
    }

    @Bean
    CommandLineRunner init(PersonRepository repository) {
        return args -> {
            Stream.of("Юра", "Цвета", "Евгенийй", "Влад", "Тарас", "Сергей").forEach(name -> repository.save(new Person(name)));
            for (Person person : repository.findAll()) {
                System.out.println(person.getName());
            }
        };
    }
}
