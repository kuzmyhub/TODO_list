package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository store;

    public Category add(Category category) {
        return store.add(category);
    }

    public List<Category> findByIds(List<String> ids) {
        return store.findByIds(ids);
    }

    public List<Category> findAll() {
        return store.findAll();
    }
}
