package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernatePriorityRepository implements PriorityRepository {

    private TemplateRepository hibernateTemplateRepository;

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
                "FROM Priority",
                Priority.class
        );
    }

    @Override
    public Optional<Priority> findById(int id) {
        return hibernateTemplateRepository.optional(
                "FROM Priority p WHERE p.id = :fId",
                Priority.class,
                Map.of("fId", id)
        );
    }

}
