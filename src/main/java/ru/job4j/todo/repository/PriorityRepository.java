package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityRepository {

    Optional<Priority> add(Priority priority);

    List<Priority> findAll();

    Optional<Priority> findById(int id);

}
