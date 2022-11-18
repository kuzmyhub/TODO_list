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
public class SimpleUserService implements UserService {

    private UserRepository hibernateUserRepository;

    @Override
    public Optional<User> add(User user) {
        return hibernateUserRepository.add(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return hibernateUserRepository.findByLoginAndPassword(login, password);
    }
}
