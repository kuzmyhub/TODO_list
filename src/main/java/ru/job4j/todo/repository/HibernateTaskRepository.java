package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final Crud crudRepository;

    @Override
    public Task add(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public List<Task> findAll(User user) {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.categories JOIN FETCH t.priority JOIN FETCH t.user u WHERE u.id = :fUserId",
                Task.class,
                Map.of("fUserId", user.getId())
        );
    }

    @Override
    public List<Task> findByDone(User user, boolean done) {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.categories JOIN FETCH t.priority JOIN FETCH t.user u WHERE u.id = :fUserId AND t.done = :fDone",
                Task.class,
                Map.of("fUserId", user.getId(), "fDone", done)
        );
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE t.id = :fId",
                Task.class,
                Map.of("fId", id));
    }

    @Override
    public void updateDone(int id, boolean done) {
        crudRepository.run(
                "UPDATE Task SET done = :fDone WHERE id = :fId",
                Map.of("fDone", done, "fId", id)
        );
    }

    @Override
    public void updateDescription(int id, String description) {
        crudRepository.run(
                "UPDATE Task SET description = :fDescription WHERE id = :fId",
                Map.of("fDescription", description, "fId", id)
        );
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE Task t WHERE t.id = :fId",
                Map.of("fId", id)
        );
    }
}
