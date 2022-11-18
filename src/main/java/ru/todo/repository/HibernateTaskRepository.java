package ru.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.todo.model.Task;
import ru.todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {

    private final TemplateRepository hibernateTemplateRepository;

    private static final String SELECT = "FROM Task t"
            + " JOIN FETCH t.categories"
            + " JOIN FETCH t.priority"
            + " JOIN FETCH t.user u WHERE u.id = :fUserId";

    private static final String BY_DONE = "AND t.done = :fDone";

    private static final String SELECT_BY_ID = "FROM Task t"
            + " JOIN FETCH t.priority"
            + " JOIN FETCH t.categories"
            + " WHERE t.id = :fId";

    private static final String UPDATE = "UPDATE Task SET";

    private static final String DONE = "done = :fDone WHERE id = :fId";

    private static final String DESCRIPTION = "description = :fDescription WHERE id = :fId";

    private static final String DELETE = "DELETE Task t WHERE t.id = :fId";

    @Override
    public Task add(Task task) {
        hibernateTemplateRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public List<Task> findAll(User user) {
        return hibernateTemplateRepository.query(
                String.format("%s", SELECT),
                Task.class,
                Map.of("fUserId", user.getId())
        );
    }

    @Override
    public List<Task> findByDone(User user, boolean done) {
        return hibernateTemplateRepository.query(
                String.format("%s %s", SELECT, BY_DONE),
                Task.class,
                Map.of("fUserId", user.getId(), "fDone", done)
        );
    }

    @Override
    public Optional<Task> findById(int id) {
        return hibernateTemplateRepository.optional(
                String.format("%s", SELECT_BY_ID),
                Task.class,
                Map.of("fId", id));
    }

    @Override
    public void updateDone(int id, boolean done) {
        hibernateTemplateRepository.run(
                String.format("%s %s", UPDATE, DONE),
                Map.of("fDone", done, "fId", id)
        );
    }

    @Override
    public void updateDescription(int id, String description) {
        hibernateTemplateRepository.run(
                String.format("%s %s", UPDATE, DESCRIPTION),
                Map.of("fDescription", description, "fId", id)
        );
    }

    @Override
    public void delete(int id) {
        hibernateTemplateRepository.run(
                String.format("%s", DELETE),
                Map.of("fId", id)
        );
    }
}
