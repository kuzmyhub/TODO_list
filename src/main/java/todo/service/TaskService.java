package todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import todo.model.Item;
import todo.store.TaskStore;

import java.util.List;

@ThreadSafe
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskStore store;

    public Item add(Item item) {
        return store.add(item);
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public List<Item> findByDoneTrue() {
        return store.findByDoneTrue();
    }

    public List<Item> findByDoneFalse() {
        return store.findByDoneFalse();
    }

    public Item findById(int id) {
        return store.findById(id);
    }
}
