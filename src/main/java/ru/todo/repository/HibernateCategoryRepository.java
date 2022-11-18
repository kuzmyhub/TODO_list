package ru.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateCategoryRepository  implements CategoryRepository {

    private TemplateRepository hibernateTemplateRepository;

    private static final String SELECT = "FROM Category c";

    private static final String BY_ID = "WHERE c.id in (:fIds)";

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
                String.format("%s", SELECT),
                Category.class
        );
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return hibernateTemplateRepository.query(
                String.format("%s %s", SELECT, BY_ID),
                Category.class,
                Map.of("fIds", ids)
        );
    }
}
