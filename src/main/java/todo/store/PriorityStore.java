package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PriorityStore {

    private CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query(
                "FROM Priority",
                Priority.class
        );
    }

    public Optional<Priority> findById(int id) {
        return crudRepository.optional(
                "FROM Priority p WHERE p.id = :fId",
                Priority.class,
                Map.of("fId", id)
        );
    }

}
