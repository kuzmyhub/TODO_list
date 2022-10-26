package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import todo.model.User;

import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class UserStore {

    private SessionFactory sf;

    public Optional<User> add(User user) {
        Session session = sf.openSession();
        Optional<User> addingUser = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            addingUser = Optional.of(user);
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return addingUser;
    }

    public Optional<User> findByLoginAndPwd(User user) {
        Session session = sf.openSession();
        Optional<User> findingUser = session.createQuery(
                "FROM User u WHERE u.login = :fLogin AND u.password = :fPassword"
                )
                .setParameter("fLogin", user.getLogin())
                .setParameter("fPassword", user.getPassword())
                .uniqueResultOptional();
        session.close();
        return findingUser;
    }
}
