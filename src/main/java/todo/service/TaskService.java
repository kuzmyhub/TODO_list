package todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import todo.model.Task;
import todo.model.User;
import todo.store.TaskStore;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class TaskService {

    private final TaskStore store;

    public Task add(Task task) {
        Task addedTask = store.add(task);
        if (addedTask.getUser() == null) {
            throw new NoSuchElementException("Task user not found");
        }
        return addedTask;
    }

    public List<Task> findAll(User user) {
        return store.findAll(user);
    }

    public List<Task> findByDone(User user, boolean done) {
        return store.findByDone(user, done);
    }

    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    public void updateDone(int id, boolean done) {
        store.updateDone(id, done);
    }

    public void updateDescription(int id, String description) {
        store.updateDescription(id, description);
    }

    public void delete(int id) {
        store.delete(id);
    }
}
