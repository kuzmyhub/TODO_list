package ru.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private TemplateRepository hibernateTemplateRepository;

    private static final String SELECT = "FROM Priority p";

    private static final String BY_ID = "WHERE p.id = :fId";

    @Override
    public Optional<Priority> add(Priority priority) {
        Optional<Priority> addingPriority;
        try {
            hibernateTemplateRepository.run(session -> session.save(priority));
            addingPriority = Optional.of(priority);
        } catch (Exception e) {
            addingPriority = Optional.empty();
        }
        return addingPriority;
    }

    @Override
    public List<Priority> findAll() {
        return hibernateTemplateRepository.query(
                String.format("%s", SELECT),
                Priority.class
        );
    }

    @Override
    public Optional<Priority> findById(int id) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                Priority.class,
                Map.of("fId", id)
        );
    }

}
