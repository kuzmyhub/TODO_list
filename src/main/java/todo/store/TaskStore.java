package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;
import org.springframework.stereotype.Repository;
import todo.model.Item;
import todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class TaskStore {

    private final CrudRepository crudRepository;

    public Item add(Item item) {
        crudRepository.run(session -> session.save(item));
        return item;
    }

    public List<Item> findAll(User user) {
        return crudRepository.query(
                "FROM Item i JOIN FETCH i.user u WHERE u.id = :fUserId",
                Item.class,
                Map.of("fUserId", user.getId())
        );
    }

    public List<Item> findByDoneTrue(User user) {
        return crudRepository.query(
                "FROM Item i WHERE i.client = :fUserId AND i.done = true",
                Item.class,
                Map.of("fUserId", user.getId())
        );
    }

    public List<Item> findByDoneFalse(User user) {
        return crudRepository.query(
                "FROM Item i WHERE i.client = :fUserId AND i.done = false",
                Item.class,
                Map.of("fUserId", user.getId())
        );
    }

    public Optional<Item> findById(int id) {
        return crudRepository.optional(
                "FROM Item i WHERE i.id = :fId",
                Item.class,
                Map.of("fId", id));
    }

    public void updateDone(int id, boolean done) {
        crudRepository.run(
                "UPDATE Item SET done = :fDone WHERE id = :fId",
                Map.of("fDone", done, "fId", id)
        );
    }

    public void updateDescription(int id, String description) {
        crudRepository.run(
                "UPDATE Item SET description = :fDescription WHERE id = :fId",
                Map.of("fDescription", description, "fId", id)
        );
    }

    public void delete(int id) {
        crudRepository.run(
                "DELETE Item i WHERE i.id = :fId",
                Map.of("fId", id)
        );
    }
}
