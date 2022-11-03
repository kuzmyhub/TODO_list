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
    public void whenAddTaskThenSetTaskId() {
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
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategorization(List.of(category));
        taskRepository.add(task);

        int notExpectedId = 0;

        assertThat(notExpectedId).isNotEqualTo(task.getId());
    }

    @Test
    public void whenAddTaskThenGetSomeTask() {
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
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategorization(List.of(category));
        taskRepository.add(task);

        Task dbTask = taskRepository
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
        assertIterableEquals(dbTask.getCategorization(), task.getCategorization());
    }

    @Test
    public void whenAddSeveralTasksThenGetSameTasks() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        PriorityRepository priorityRepository = new PriorityRepository(crudRepository);
        CategoryRepository categoryRepository = new CategoryRepository(crudRepository);
        TaskRepository taskRepository = new TaskRepository(crudRepository);

        User userFirstTask = new User();
        userFirstTask.setName("Kujo");
        userFirstTask.setLogin("Jojo");
        userFirstTask.setPassword("88005553535");
        userFirstTask.setUtc("UTC+2");
        userRepository.add(userFirstTask);

        Priority priorityFirstTask = new Priority();
        priorityFirstTask.setName("not urgent");
        priorityFirstTask.setPosition(3);
        priorityRepository.add(priorityFirstTask);

        Category categoryFirstTask = new Category();
        categoryFirstTask.setName("java");
        categoryRepository.add(categoryFirstTask);

        Task firstTask = new Task();
        firstTask.setDescription("test");
        firstTask.setUser(userFirstTask);
        firstTask.setPriority(priorityFirstTask);
        firstTask.setCategorization(List.of(categoryFirstTask));
        taskRepository.add(firstTask);

        User userSecondTask = new User();
        userSecondTask.setName("Jolyne");
        userSecondTask.setLogin("Putci");
        userSecondTask.setPassword("hesoyam");
        userSecondTask.setUtc("UTC+2");
        userRepository.add(userSecondTask);

        Priority prioritySecondTask = new Priority();
        prioritySecondTask.setName("high priority");
        prioritySecondTask.setPosition(4);
        priorityRepository.add(prioritySecondTask);

        Category categorySecondTask = new Category();
        categorySecondTask.setName("home");
        categoryRepository.add(categorySecondTask);

        Task secondTask = new Task();
        secondTask.setDescription("junit");
        secondTask.setUser(userSecondTask);
        secondTask.setPriority(prioritySecondTask);
        secondTask.setCategorization(List.of(categorySecondTask));
        taskRepository.add(secondTask);

        Task firstDbTask = taskRepository
                .findById(firstTask.getId())
                .get();
        Task secondDbTask = taskRepository
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
        assertIterableEquals(firstDbTask.getCategorization(), firstTask.getCategorization());

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
        assertIterableEquals(secondDbTask.getCategorization(), secondTask.getCategorization());
    }

    @Test
    public void whenAddTasksThenGetByUser() {
        CrudRepository crudRepository = new CrudRepository(sf);
        UserRepository userRepository = new UserRepository(crudRepository);
        PriorityRepository priorityRepository = new PriorityRepository(crudRepository);
        CategoryRepository categoryRepository = new CategoryRepository(crudRepository);
        TaskRepository taskRepository = new TaskRepository(crudRepository);

        User userFirstTask = new User();
        userFirstTask.setName("Kujo");
        userFirstTask.setLogin("Jojo");
        userFirstTask.setPassword("88005553535");
        userFirstTask.setUtc("UTC+2");
        userRepository.add(userFirstTask);

        Priority priorityFirstTask = new Priority();
        priorityFirstTask.setName("not urgent");
        priorityFirstTask.setPosition(3);
        priorityRepository.add(priorityFirstTask);

        Category categoryFirstTask = new Category();
        categoryFirstTask.setName("java");
        categoryRepository.add(categoryFirstTask);

        Task firstTask = new Task();
        firstTask.setDescription("test");
        firstTask.setUser(userFirstTask);
        firstTask.setPriority(priorityFirstTask);
        firstTask.setCategorization(List.of(categoryFirstTask));
        taskRepository.add(firstTask);

        User userSecondTask = new User();
        userSecondTask.setName("Jolyne");
        userSecondTask.setLogin("Putci");
        userSecondTask.setPassword("hesoyam");
        userSecondTask.setUtc("UTC+2");
        userRepository.add(userSecondTask);

        Priority prioritySecondTask = new Priority();
        prioritySecondTask.setName("high priority");
        prioritySecondTask.setPosition(4);
        priorityRepository.add(prioritySecondTask);

        Category categorySecondTask = new Category();
        categorySecondTask.setName("home");
        categoryRepository.add(categorySecondTask);

        Task secondTask = new Task();
        secondTask.setDescription("junit");
        secondTask.setUser(userSecondTask);
        secondTask.setPriority(prioritySecondTask);
        secondTask.setCategorization(List.of(categorySecondTask));
        taskRepository.add(secondTask);

        List<Task> dbTasks = taskRepository.findAll(userFirstTask);
        Task dbTask = taskRepository
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
        assertIterableEquals(dbTask.getCategorization(), firstTask.getCategorization());
    }

    @Test
    public void whenAddTasksThenGetByDone() {
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

        Task taskFalse = new Task();
        taskFalse.setDescription("test");
        taskFalse.setUser(user);
        taskFalse.setPriority(priority);
        taskFalse.setCategorization(List.of(category));
        taskRepository.add(taskFalse);

        Task taskTrue = new Task();
        taskTrue.setDescription("test");
        taskTrue.setUser(user);
        taskTrue.setDone(true);
        taskTrue.setPriority(priority);
        taskTrue.setCategorization(List.of(category));
        taskRepository.add(taskTrue);

        List<Task> dbTasks = taskRepository
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
        assertIterableEquals(dbTask.getCategorization(), taskTrue.getCategorization());
    }

    @Test
    public void whenUpdateDone() {
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
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategorization(List.of(category));
        taskRepository.add(task);

        taskRepository.updateDone(task.getId(), true);

        assertTrue(taskRepository.findById(task.getId()).get().isDone());
    }

    @Test
    public void whenUpdateDescription() {
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
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategorization(List.of(category));
        taskRepository.add(task);

        String expected = "update description";
        taskRepository.updateDescription(task.getId(), expected);

        assertThat(taskRepository.findById(task.getId()).get().getDescription())
                .isEqualTo(expected);
    }

    @Test
    public void whenDeleteTaskThenOptionalIsEmpty() {
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
        task.setDescription("test");
        task.setUser(user);
        task.setPriority(priority);
        task.setCategorization(List.of(category));
        taskRepository.add(task);

        int id = task.getId();
        taskRepository.delete(id);

        assertTrue(taskRepository.findById(id).isEmpty());
    }
}