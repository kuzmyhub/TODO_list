package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface CategoryRepository {

    Category add(Category category);

    List<Category> findAll();

    List<Category> findByIds(List<String> ids);
}
