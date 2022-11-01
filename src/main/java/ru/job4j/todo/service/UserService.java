package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository store;

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }
}
