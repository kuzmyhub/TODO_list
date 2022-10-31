package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CategoryStore {

    private CrudRepository crudRepository;

    public List<Category> findAll() {
        return crudRepository.query(
                "FROM Category",
                Category.class
        );
    }

    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "FROM Category WHERE id = :fId",
                Category.class,
                Map.of("fId", id)
        );
    }
}
