package ru.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.todo.model.Category;
import ru.todo.model.Priority;
import ru.todo.model.Task;
import ru.todo.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateTaskRepositoryTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void createSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterEach
    public void cleanTable() {
        Session session = sessionFactory.openSession();
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
    public void whenAddTaskThenSetTaskId() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        hibernateUserRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        hibernatePriorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        hibernateCategoryRepository.add(category);

        Task task = new Task();
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategories(List.of(category));
        hibernateTaskRepository.add(task);

        int notExpectedId = 0;

        assertThat(notExpectedId).isNotEqualTo(task.getId());
    }

    @Test
    public void whenAddTaskThenGetSomeTask() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        hibernateUserRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        hibernatePriorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        hibernateCategoryRepository.add(category);

        Task task = new Task();
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategories(List.of(category));
        hibernateTaskRepository.add(task);

        Task dbTask = hibernateTaskRepository
                .findById(task.getId())
                .get();

        assertThat(dbTask.getId()).isEqualTo(task.getId());
        assertThat(dbTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(dbTask.getCreated().getYear())
                .isEqualTo(task.getCreated().getYear());
        assertThat(dbTask.getCreated().getMonth())
                .isEqualTo(task.getCreated().getMonth());
        assertThat(dbTask.getCreated().getDayOfWeek())
                .isEqualTo(task.getCreated().getDayOfWeek());
        assertThat(dbTask.getCreated().getHour())
                .isEqualTo(task.getCreated().getHour());
        assertThat(dbTask.getCreated().getMinute())
                .isEqualTo(task.getCreated().getMinute());
        assertThat(dbTask.getCreated().getSecond())
                .isEqualTo(task.getCreated().getSecond());
        assertThat(dbTask.isDone()).isEqualTo(task.isDone());
        assertThat(dbTask.getUser()).isEqualTo(task.getUser());
        assertThat(dbTask.getPriority()).isEqualTo(task.getPriority());
        assertThat(dbTask.getUser()).isEqualTo(task.getUser());
        assertIterableEquals(dbTask.getCategories(), task.getCategories());
    }

    @Test
    public void whenAddSeveralTasksThenGetSameTasks() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User userFirstTask = new User();
        userFirstTask.setName("Kujo");
        userFirstTask.setLogin("Jojo");
        userFirstTask.setPassword("88005553535");
        userFirstTask.setUtc("UTC+2");
        hibernateUserRepository.add(userFirstTask);

        Priority priorityFirstTask = new Priority();
        priorityFirstTask.setName("not urgent");
        priorityFirstTask.setPosition(3);
        hibernatePriorityRepository.add(priorityFirstTask);

        Category categoryFirstTask = new Category();
        categoryFirstTask.setName("java");
        hibernateCategoryRepository.add(categoryFirstTask);

        Task firstTask = new Task();
        firstTask.setDescription("test");
        firstTask.setUser(userFirstTask);
        firstTask.setPriority(priorityFirstTask);
        firstTask.setCategories(List.of(categoryFirstTask));
        hibernateTaskRepository.add(firstTask);

        User userSecondTask = new User();
        userSecondTask.setName("Jolyne");
        userSecondTask.setLogin("Putci");
        userSecondTask.setPassword("hesoyam");
        userSecondTask.setUtc("UTC+2");
        hibernateUserRepository.add(userSecondTask);

        Priority prioritySecondTask = new Priority();
        prioritySecondTask.setName("high priority");
        prioritySecondTask.setPosition(4);
        hibernatePriorityRepository.add(prioritySecondTask);

        Category categorySecondTask = new Category();
        categorySecondTask.setName("home");
        hibernateCategoryRepository.add(categorySecondTask);

        Task secondTask = new Task();
        secondTask.setDescription("junit");
        secondTask.setUser(userSecondTask);
        secondTask.setPriority(prioritySecondTask);
        secondTask.setCategories(List.of(categorySecondTask));
        hibernateTaskRepository.add(secondTask);

        Task firstDbTask = hibernateTaskRepository
                .findById(firstTask.getId())
                .get();
        Task secondDbTask = hibernateTaskRepository
                .findById(secondTask.getId())
                .get();

        assertThat(firstDbTask.getId()).isEqualTo(firstTask.getId());
        assertThat(firstDbTask.getDescription()).isEqualTo(firstTask.getDescription());
        assertThat(firstDbTask.getCreated().getYear())
                .isEqualTo(firstTask.getCreated().getYear());
        assertThat(firstDbTask.getCreated().getMonth())
                .isEqualTo(firstTask.getCreated().getMonth());
        assertThat(firstDbTask.getCreated().getDayOfWeek())
                .isEqualTo(firstTask.getCreated().getDayOfWeek());
        assertThat(firstDbTask.getCreated().getHour())
                .isEqualTo(firstTask.getCreated().getHour());
        assertThat(firstDbTask.getCreated().getMinute())
                .isEqualTo(firstTask.getCreated().getMinute());
        assertThat(firstDbTask.getCreated().getSecond())
                .isEqualTo(firstTask.getCreated().getSecond());
        assertThat(firstDbTask.isDone()).isEqualTo(firstTask.isDone());
        assertThat(firstDbTask.getUser()).isEqualTo(firstTask.getUser());
        assertThat(firstDbTask.getPriority()).isEqualTo(firstTask.getPriority());
        assertThat(firstDbTask.getUser()).isEqualTo(firstTask.getUser());
        assertIterableEquals(firstDbTask.getCategories(), firstTask.getCategories());

        assertThat(secondDbTask.getId()).isEqualTo(secondTask.getId());
        assertThat(secondDbTask.getDescription()).isEqualTo(secondTask.getDescription());
        assertThat(secondDbTask.getCreated().getYear())
                .isEqualTo(secondTask.getCreated().getYear());
        assertThat(secondDbTask.getCreated().getMonth())
                .isEqualTo(secondTask.getCreated().getMonth());
        assertThat(secondDbTask.getCreated().getDayOfWeek())
                .isEqualTo(secondTask.getCreated().getDayOfWeek());
        assertThat(secondDbTask.getCreated().getHour())
                .isEqualTo(secondTask.getCreated().getHour());
        assertThat(secondDbTask.getCreated().getMinute())
                .isEqualTo(secondTask.getCreated().getMinute());
        assertThat(secondDbTask.getCreated().getSecond())
                .isEqualTo(secondTask.getCreated().getSecond());
        assertThat(secondDbTask.isDone()).isEqualTo(secondTask.isDone());
        assertThat(secondDbTask.getUser()).isEqualTo(secondTask.getUser());
        assertThat(secondDbTask.getPriority()).isEqualTo(secondTask.getPriority());
        assertThat(secondDbTask.getUser()).isEqualTo(secondTask.getUser());
        assertIterableEquals(secondDbTask.getCategories(), secondTask.getCategories());
    }

    @Test
    public void whenAddTasksThenGetByUser() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User userFirstTask = new User();
        userFirstTask.setName("Kujo");
        userFirstTask.setLogin("Jojo");
        userFirstTask.setPassword("88005553535");
        userFirstTask.setUtc("UTC+2");
        hibernateUserRepository.add(userFirstTask);

        Priority priorityFirstTask = new Priority();
        priorityFirstTask.setName("not urgent");
        priorityFirstTask.setPosition(3);
        hibernatePriorityRepository.add(priorityFirstTask);

        Category categoryFirstTask = new Category();
        categoryFirstTask.setName("java");
        hibernateCategoryRepository.add(categoryFirstTask);

        Task firstTask = new Task();
        firstTask.setDescription("test");
        firstTask.setUser(userFirstTask);
        firstTask.setPriority(priorityFirstTask);
        firstTask.setCategories(List.of(categoryFirstTask));
        hibernateTaskRepository.add(firstTask);

        User userSecondTask = new User();
        userSecondTask.setName("Jolyne");
        userSecondTask.setLogin("Putci");
        userSecondTask.setPassword("hesoyam");
        userSecondTask.setUtc("UTC+2");
        hibernateUserRepository.add(userSecondTask);

        Priority prioritySecondTask = new Priority();
        prioritySecondTask.setName("high priority");
        prioritySecondTask.setPosition(4);
        hibernatePriorityRepository.add(prioritySecondTask);

        Category categorySecondTask = new Category();
        categorySecondTask.setName("home");
        hibernateCategoryRepository.add(categorySecondTask);

        Task secondTask = new Task();
        secondTask.setDescription("junit");
        secondTask.setUser(userSecondTask);
        secondTask.setPriority(prioritySecondTask);
        secondTask.setCategories(List.of(categorySecondTask));
        hibernateTaskRepository.add(secondTask);

        List<Task> dbTasks = hibernateTaskRepository.findAll(userFirstTask);
        Task dbTask = hibernateTaskRepository
                .findAll(userFirstTask).get(0);
        int numberOfTasks = 1;

        assertThat(dbTasks.size()).isEqualTo(numberOfTasks);
        assertThat(dbTask.getId()).isEqualTo(firstTask.getId());
        assertThat(dbTask.getDescription()).isEqualTo(firstTask.getDescription());
        assertThat(dbTask.getCreated().getYear())
                .isEqualTo(firstTask.getCreated().getYear());
        assertThat(dbTask.getCreated().getMonth())
                .isEqualTo(firstTask.getCreated().getMonth());
        assertThat(dbTask.getCreated().getDayOfWeek())
                .isEqualTo(firstTask.getCreated().getDayOfWeek());
        assertThat(dbTask.getCreated().getHour())
                .isEqualTo(firstTask.getCreated().getHour());
        assertThat(dbTask.getCreated().getMinute())
                .isEqualTo(firstTask.getCreated().getMinute());
        assertThat(dbTask.getCreated().getSecond())
                .isEqualTo(firstTask.getCreated().getSecond());
        assertThat(dbTask.isDone()).isEqualTo(firstTask.isDone());
        assertThat(dbTask.getUser()).isEqualTo(firstTask.getUser());
        assertThat(dbTask.getPriority()).isEqualTo(firstTask.getPriority());
        assertThat(dbTask.getUser()).isEqualTo(firstTask.getUser());
        assertIterableEquals(dbTask.getCategories(), firstTask.getCategories());
    }

    @Test
    public void whenAddTasksThenGetByDone() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        hibernateUserRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        hibernatePriorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        hibernateCategoryRepository.add(category);

        Task taskFalse = new Task();
        taskFalse.setDescription("test");
        taskFalse.setUser(user);
        taskFalse.setPriority(priority);
        taskFalse.setCategories(List.of(category));
        hibernateTaskRepository.add(taskFalse);

        Task taskTrue = new Task();
        taskTrue.setDescription("test");
        taskTrue.setUser(user);
        taskTrue.setDone(true);
        taskTrue.setPriority(priority);
        taskTrue.setCategories(List.of(category));
        hibernateTaskRepository.add(taskTrue);

        List<Task> dbTasks = hibernateTaskRepository
                .findByDone(user, true);
        Task dbTask = dbTasks.get(0);
        int numberOfTasks = 1;

        assertThat(dbTasks.size()).isEqualTo(numberOfTasks);
        assertThat(dbTask.getDescription()).isEqualTo(taskTrue.getDescription());
        assertThat(dbTask.getCreated().getYear())
                .isEqualTo(taskTrue.getCreated().getYear());
        assertThat(dbTask.getCreated().getMonth())
                .isEqualTo(taskTrue.getCreated().getMonth());
        assertThat(dbTask.getCreated().getDayOfWeek())
                .isEqualTo(taskTrue.getCreated().getDayOfWeek());
        assertThat(dbTask.getCreated().getHour())
                .isEqualTo(taskTrue.getCreated().getHour());
        assertThat(dbTask.getCreated().getMinute())
                .isEqualTo(taskTrue.getCreated().getMinute());
        assertThat(dbTask.getCreated().getSecond())
                .isEqualTo(taskTrue.getCreated().getSecond());
        assertThat(dbTask.isDone()).isEqualTo(taskTrue.isDone());
        assertThat(dbTask.getUser()).isEqualTo(taskTrue.getUser());
        assertThat(dbTask.getPriority()).isEqualTo(taskTrue.getPriority());
        assertThat(dbTask.getUser()).isEqualTo(taskTrue.getUser());
        assertIterableEquals(dbTask.getCategories(), taskTrue.getCategories());
    }

    @Test
    public void whenUpdateDone() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        hibernateUserRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        hibernatePriorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        hibernateCategoryRepository.add(category);

        Task task = new Task();
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategories(List.of(category));
        hibernateTaskRepository.add(task);

        hibernateTaskRepository.updateDone(task.getId(), true);

        assertTrue(hibernateTaskRepository.findById(task.getId()).get().isDone());
    }

    @Test
    public void whenUpdateDescription() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        hibernateUserRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        hibernatePriorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        hibernateCategoryRepository.add(category);

        Task task = new Task();
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategories(List.of(category));
        hibernateTaskRepository.add(task);

        String expected = "update description";
        hibernateTaskRepository.updateDescription(task.getId(), expected);

        assertThat(hibernateTaskRepository.findById(task.getId()).get().getDescription())
                .isEqualTo(expected);
    }

    @Test
    public void whenDeleteTaskThenOptionalIsEmpty() {
        TemplateRepository crudRepository = new HibernateTemplateRepository(sessionFactory);
        HibernateUserRepository hibernateUserRepository = new HibernateUserRepository(crudRepository);
        HibernatePriorityRepository hibernatePriorityRepository = new HibernatePriorityRepository(crudRepository);
        HibernateCategoryRepository hibernateCategoryRepository = new HibernateCategoryRepository(crudRepository);
        HibernateTaskRepository hibernateTaskRepository = new HibernateTaskRepository(crudRepository);

        User user = new User();
        user.setName("Kujo");
        user.setLogin("Jojo");
        user.setPassword("88005553535");
        user.setUtc("UTC+2");
        hibernateUserRepository.add(user);

        Priority priority = new Priority();
        priority.setName("not urgent");
        priority.setPosition(3);
        hibernatePriorityRepository.add(priority);

        Category category = new Category();
        category.setName("java");
        hibernateCategoryRepository.add(category);

        Task task = new Task();
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategories(List.of(category));
        hibernateTaskRepository.add(task);

        int id = task.getId();
        hibernateTaskRepository.delete(id);

        assertTrue(hibernateTaskRepository.findById(id).isEmpty());
    }
}