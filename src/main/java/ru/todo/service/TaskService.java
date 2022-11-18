package ru.todo.service;

import ru.todo.model.Task;
import ru.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task add(Task task);

    List<Task> findAll(User user);

    List<Task> findByDone(User user, boolean done);

    Optional<Task> findById(int id);

    void updateDone(int id, boolean done);

    void updateDescription(int id, String description);

    void delete(int id);

}
