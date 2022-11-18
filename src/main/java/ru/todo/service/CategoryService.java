package ru.todo.service;

import ru.todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> add(Category category);

    List<Category> findByIds(List<Integer> ids);

    List<Category> findAll();

}
