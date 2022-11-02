package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

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
                "DELETE FROM User"
        )
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddUserThenGetSameUser() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        User user = new User();
        user.setId(1);
        user.setName("van");
        user.setLogin("van");
        user.setPassword("pass");
        user.setUtc("UTC+2");
        Optional<User> u = userRepository.add(user);
        System.out.println("opop");
        System.out.println(u.get() + "opop");
        User dbUser = userRepository.
                findByLoginAndPassword(
                        user.getLogin(), user.getPassword()
                )
                .get();
        assertThat(dbUser).isEqualTo(user);
    }
}