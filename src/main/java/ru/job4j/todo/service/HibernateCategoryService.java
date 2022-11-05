package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;

@ThreadSafe
@Service
@AllArgsConstructor
public class HibernateCategoryService implements CategoryService {

    private CategoryRepository store;

    @Override
    public Category add(Category category) {
        return store.add(category);
    }

    @Override
    public List<Category> findByIds(List<String> ids) {
        return store.findByIds(ids);
    }

    @Override
    public List<Category> findAll() {
        return store.findAll();
    }
}