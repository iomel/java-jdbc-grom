package lesson7;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralDAO <T> {

    static SessionFactory factory;

    public GeneralDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    // Find
    List<T> findByParam(String sqlQuery, HashMap<String, Object> params) {
        List<T> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery(sqlQuery);
            for (Map.Entry<String, Object> param : params.entrySet())
                query.setParameter(param.getKey(), param.getValue());
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete
    public void delete(T t) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // save
    public void save(T t) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // update
    public void update(T t) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(t);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

}