package todo.store;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import todo.model.Item;

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

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List list = session.createQuery(
                "FROM Item", Item.class
        ).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Item> findByDoneTrue() {
        Session session = sf.openSession();
        List list = session.createQuery("FROM Item i WHERE i.done = true").list();
        session.close();
        return list;
    }

    public List<Item> findByDoneFalse() {
        Session session = sf.openSession();
        List list = session.createQuery("FROM Item i WHERE i.done = false").list();
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
        session.beginTransaction();
        session.createQuery(
                "UPDATE Item SET done = :fDone WHERE id = :fId"
                )
                .setParameter("fDone", done)
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void updateDescription(int id, String description) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "UPDATE Item SET description = :fDescription WHERE id = :fId"
                )
                .setParameter("fDescription", description)
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
