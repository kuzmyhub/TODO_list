package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import todo.model.Category;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CategoryStore {

    private CrudRepository crudRepository;

    public Optional<Category> findByName(String name) {
        return crudRepository.optional(
                "FROM Category WHERE name = :fName",
                Category.class,
                Map.of("fName", name)
        );
    }
}
