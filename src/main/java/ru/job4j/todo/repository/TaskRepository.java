package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task add(Task task);

    List<Task> findAll(User user);

    List<Task> findByDone(User user, boolean done);

    Optional<Task> findById(int id);

    void updateDone(int id, boolean done);

    void updateDescription(int id, String description);

    void delete(int id);

}
