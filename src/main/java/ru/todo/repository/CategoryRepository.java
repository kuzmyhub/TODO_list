package ru.todo.repository;

import ru.todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> add(Category category);

    List<Category> findAll();

    List<Category> findByIds(List<Integer> ids);
}
