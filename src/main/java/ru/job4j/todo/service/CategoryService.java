package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> add(Category category);

    List<Category> findByIds(List<Integer> ids);

    List<Category> findAll();

}
