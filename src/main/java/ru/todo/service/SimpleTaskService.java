package ru.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.todo.model.Task;
import ru.todo.model.User;
import ru.todo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskRepository hibernateTaskRepository;

    @Override
    public Task add(Task task) {
        Task addedTask = hibernateTaskRepository.add(task);
        if (addedTask.getUser() == null) {
            throw new NoSuchElementException("Task user not found");
        }
        return addedTask;
    }

    @Override
    public List<Task> findAll(User user) {
        return hibernateTaskRepository.findAll(user);
    }

    @Override
    public List<Task> findByDone(User user, boolean done) {
        return hibernateTaskRepository.findByDone(user, done);
    }

    @Override
    public Optional<Task> findById(int id) {
        return hibernateTaskRepository.findById(id);
    }

    @Override
    public void updateDone(int id, boolean done) {
        hibernateTaskRepository.updateDone(id, done);
    }

    @Override
    public void updateDescription(int id, String description) {
        hibernateTaskRepository.updateDescription(id, description);
    }

    @Override
    public void delete(int id) {
        hibernateTaskRepository.delete(id);
    }
}
