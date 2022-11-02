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
    public void whenAddUserThenSetUserId() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        userRepository.add(user);
        int notExpectedId = 0;
        assertThat(user.getId()).isNotEqualTo(notExpectedId);
    }

    @Test
    public void whenAddUserThenGetSameUser() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        userRepository.add(user);
        User dbUser = userRepository.
                findByLoginAndPassword(
                        user.getLogin(), user.getPassword()
                )
                .get();
        assertThat(dbUser).isEqualTo(user);
    }

    @Test
    public void whenAddSeveralUsersThenGetSameUsers() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        User firstUser = new User();
        firstUser.setName("Kujo");
        firstUser.setLogin("Jojo");
        firstUser.setPassword("88005553535");
        firstUser.setUtc("UTC+2");
        User secondUser = new User();
        secondUser.setName("Jolyne");
        secondUser.setLogin("Putci");
        secondUser.setPassword("hesoyam");
        secondUser.setUtc("UTC+2");
        userRepository.add(firstUser);
        userRepository.add(secondUser);
        User dbFirstUser = userRepository.
                findByLoginAndPassword(
                        firstUser.getLogin(), firstUser.getPassword()
                )
                .get();
        User dbSecondUser = userRepository.
                findByLoginAndPassword(
                        secondUser.getLogin(), secondUser.getPassword()
                )
                .get();
        assertThat(dbFirstUser).isEqualTo(firstUser);
        assertThat(dbSecondUser).isEqualTo(secondUser);
    }
}