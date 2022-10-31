package todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import todo.model.Category;
import todo.store.CategoryStore;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryStore store;

    public Optional<Category> findByName(String name) {
        return store.findByName(name);
    }
}
