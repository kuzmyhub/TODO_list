package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {

    private static SessionFactory sf;

    @BeforeAll
    public static void createSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sf = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterEach
    public void cleanTable() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "DELETE FROM Task"
                )
                .executeUpdate();
        session.createQuery(
                        "DELETE FROM User"
                )
                .executeUpdate();
        session.createQuery(
                        "DELETE FROM Priority"
                )
                .executeUpdate();
        session.createQuery(
                        "DELETE FROM Category"
                )
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddTaskThenSetUserId() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        PriorityRepository priorityRepository = new PriorityRepository(crudRepository);
        CategoryRepository categoryRepository = new CategoryRepository(crudRepository);
        TaskRepository taskRepository = new TaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        userRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        priorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        categoryRepository.add(category);

        Task task = new Task();
        // конвертировать в timestamp а потом обратно в zonetime
        task.setCreated(ZonedDateTime.of(LocalDate.of( 2016, 1, 4), LocalTime.of(9, 30), ZoneId.of("Europe/Moscow")));
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategorization(List.of(category));
        taskRepository.add(task);

        Task dbTask = taskRepository
                .findById(task.getId())
                .get();
        assertThat(dbTask).isEqualTo(task);
    }

}