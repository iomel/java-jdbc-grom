package lesson7;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RoomDAO {
    private static SessionFactory factory;

    public RoomDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    // save
    public void save(Room room) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

}
