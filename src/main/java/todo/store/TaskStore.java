package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import todo.model.Task;
import todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class TaskStore {

    private final CrudRepository crudRepository;

    public Task add(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    public List<Task> findAll(User user) {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.priority JOIN FETCH t.user u WHERE u.id = :fUserId",
                Task.class,
                Map.of("fUserId", user.getId())
        );
    }

    public List<Task> findByDone(User user, boolean done) {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.priority JOIN FETCH t.user u WHERE u.id = :fUserId AND t.done = :fDone",
                Task.class,
                Map.of("fUserId", user.getId(), "fDone", done)
        );
    }

    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "FROM Task t WHERE t.id = :fId",
                Task.class,
                Map.of("fId", id));
    }

    public void updateDone(int id, boolean done) {
        crudRepository.run(
                "UPDATE Task SET done = :fDone WHERE id = :fId",
                Map.of("fDone", done, "fId", id)
        );
    }

    public void updateDescription(int id, String description) {
        crudRepository.run(
                "UPDATE Task SET description = :fDescription WHERE id = :fId",
                Map.of("fDescription", description, "fId", id)
        );
    }

    public void delete(int id) {
        crudRepository.run(
                "DELETE Task t WHERE t.id = :fId",
                Map.of("fId", id)
        );
    }
}
