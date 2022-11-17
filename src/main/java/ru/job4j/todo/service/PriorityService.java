package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityService {

    Optional<Priority> add(Priority priority);

    List<Priority> findAll();

    Optional<Priority> findById(int id);

}
