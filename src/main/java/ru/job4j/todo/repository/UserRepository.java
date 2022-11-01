package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class UserRepository {

    private final CrudRepository crudRepository;

    public Optional<User> add(User user) {
        Optional<User> addingUser;
        try {
            crudRepository.run(session -> session.save(user));
            addingUser = Optional.of(user);
        } catch (Exception e) {
            addingUser = Optional.empty();
        }
        return addingUser;
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "FROM User u WHERE u.login = :fLogin AND u.password = :fPassword",
                User.class,
                Map.of("fLogin", login, "fPassword", password)
        );
    }
}
