package todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import todo.model.User;
import todo.store.UserStore;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class UserService {

    private UserStore store;

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findByLoginAndPassword(User user) {
        return store.findByLoginAndPassword(user);
    }
}
