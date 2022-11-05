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
public class HibernateUserService implements UserService {

    private UserRepository store;

    @Override
    public Optional<User> add(User user) {
        return store.add(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }
}
