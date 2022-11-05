package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class HibernateTaskService implements TaskService {

    private final TaskRepository store;

    @Override
    public Task add(Task task) {
        Task addedTask = store.add(task);
        if (addedTask.getUser() == null) {
            throw new NoSuchElementException("Task user not found");
        }
        return addedTask;
    }

    @Override
    public List<Task> findAll(User user) {
        return store.findAll(user);
    }

    @Override
    public List<Task> findByDone(User user, boolean done) {
        return store.findByDone(user, done);
    }

    @Override
    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    @Override
    public void updateDone(int id, boolean done) {
        store.updateDone(id, done);
    }

    @Override
    public void updateDescription(int id, String description) {
        store.updateDescription(id, description);
    }

    @Override
    public void delete(int id) {
        store.delete(id);
    }
}
