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
}
