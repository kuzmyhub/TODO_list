package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface CategoryService {

    Category add(Category category);

    List<Category> findByIds(List<String> ids);

    List<Category> findAll();

}
