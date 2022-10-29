package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import todo.model.User;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class UserStore {

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

    public Optional<User> findByLoginAndPassword(User user) {
        return crudRepository.optional(
                "FROM User u WHERE u.login = :fLogin AND u.password = :fPassword",
                User.class,
                Map.of("fLogin", user.getLogin(), "fPassword", user.getPassword())
        );
    }
}
