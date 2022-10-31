package todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import todo.model.Priority;
import todo.store.PriorityStore;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class PriorityService {

    private PriorityStore store;

    public List<Priority> findAll() {
        return store.findAll();
    }

    public Optional<Priority> findById(int id) {
        return store.findById(id);
    }
}
