package ru.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.todo.model.User;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final TemplateRepository hibernateTemplateRepository;

    private static final String SELECT = "FROM User u";

    private static final String BY_LOGIN_AND_PASSWORD = "WHERE u.login = :fLogin"
            + " AND u.password = :fPassword";

    @Override
    public Optional<User> add(User user) {
        Optional<User> addingUser;
        try {
            hibernateTemplateRepository.run(session -> session.save(user));
            addingUser = Optional.of(user);
        } catch (Exception e) {
            addingUser = Optional.empty();
        }
        return addingUser;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_LOGIN_AND_PASSWORD),
                User.class,
                Map.of("fLogin", login, "fPassword", password)
        );
    }
}
