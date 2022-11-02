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
public class PriorityRepository {

    private CrudRepository crudRepository;

    public Priority add(Priority priority) {
        crudRepository.run(session -> session.save(priority));
        return priority;
    }

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
