package ru.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.todo.model.Category;
import ru.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private CategoryRepository hibernateCategoryRepository;

    @Override
    public Optional<Category> add(Category category) {
        return hibernateCategoryRepository.add(category);
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return hibernateCategoryRepository.findByIds(ids);
    }

    @Override
    public List<Category> findAll() {
        return hibernateCategoryRepository.findAll();
    }
}
