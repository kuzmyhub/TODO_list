package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateCategoryRepository  implements CategoryRepository {

    private TemplateRepository hibernateTemplateRepository;

    @Override
    public Optional<Category> add(Category category) {
        Optional<Category> addingCategory;
        try {
            hibernateTemplateRepository.run(session -> session.save(category));
            addingCategory = Optional.of(category);
        } catch (Exception e) {
            addingCategory = Optional.empty();
        }
        return addingCategory;
    }

    @Override
    public List<Category> findAll() {
        return hibernateTemplateRepository.query(
                "FROM Category",
                Category.class
        );
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return hibernateTemplateRepository.query(
                "FROM Category c WHERE c.id in (:fIds)",
                Category.class,
                Map.of("fIds", ids)
        );
    }
}
