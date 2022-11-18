package ru.todo.service;

import ru.todo.model.User;

import java.util.Optional;

public interface UserService {

    public Optional<User> add(User user);

    public Optional<User> findByLoginAndPassword(String login, String password);
}
