package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CategoryRepository {

    private CrudRepository crudRepository;

    public Category add(Category category) {
        crudRepository.run(session -> session.save(category));
        return category;
    }

    public List<Category> findAll() {
        return crudRepository.query(
                "FROM Category",
                Category.class
        );
    }

    public List<Category> findByIds(List<String> ids) {
        List<Integer> intIds = ids
                .stream()
                .map(Integer::parseInt)
                .toList();
        return crudRepository.query(
                "FROM Category c WHERE c.id in (:fIds)",
                Category.class,
                Map.of("fIds", intIds)
        );
    }
}
