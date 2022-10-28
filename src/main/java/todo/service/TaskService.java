package todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import todo.model.Item;
import todo.model.User;
import todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class TaskService {

    private final TaskStore store;

    public Item add(Item item) {
        return store.add(item);
    }

    public List<Item> findAll(User user) {
        return store.findAll(user);
    }

    public List<Item> findByDoneTrue(User user) {
        return store.findByDoneTrue(user);
    }

    public List<Item> findByDoneFalse(User user) {
        return store.findByDoneFalse(user);
    }

    public Optional<Item> findById(int id) {
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
