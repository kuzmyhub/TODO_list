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

@ThreadSafe
@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> findAll(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        List list = session.createQuery(
                "FROM Item i WHERE i.client = :fUserId", Item.class
        )
                .setParameter("fUserId", user.getId())
                .list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Item> findByDoneTrue(User user) {
        Session session = sf.openSession();
        List list = session.createQuery(
                "FROM Item i WHERE i.client = :fUserId AND i.done = true"
        )
                .setParameter("fUserId", user.getId())
                .list();
        session.close();
        return list;
    }

    public List<Item> findByDoneFalse(User user) {
        Session session = sf.openSession();
        List list = session.createQuery(
                "FROM Item i WHERE i.client = :fUserId AND i.done = false"
        )
                .setParameter("fUserId", user.getId())
                .list();
        session.close();
        return list;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        Object item = session.createQuery(
                "FROM Item i WHERE i.id = :fId"
        )
                .setParameter("fId", id)
                .getSingleResult();
        session.close();
        return (Item) item;
    }

    public void updateDone(int id, boolean done) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Item SET done = :fDone WHERE id = :fId"
                    )
                    .setParameter("fDone", done)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void updateDescription(int id, String description) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Item SET description = :fDescription WHERE id = :fId"
                    )
                    .setParameter("fDescription", description)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void delete(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Item i WHERE i.id = :fId"
                    )
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
