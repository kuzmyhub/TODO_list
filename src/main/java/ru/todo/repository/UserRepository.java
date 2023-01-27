package ru.todo.repository;

import ru.todo.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> add(User user);

    Optional<User> findByLoginAndPassword(String login, String password);
}
