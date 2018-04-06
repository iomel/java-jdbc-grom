package lesson6.HQL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;

    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }


    public Product findById(Long id) {
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where id = :id ");
            query.setParameter("id", id);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    public List<Product> findByName(String name) {
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where name = :name ");
            query.setParameter("name", name);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByContainedName(String name){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where name like :name ");
            query.setParameter("name", "%" + name + "%");
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByPrice(int price, int delta){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where price between :mDelta and :pDelta");
            query.setParameter("mDelta", price - delta);
            query.setParameter("pDelta", price + delta);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByNameSortedAsc(String name){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where name = :name order by name asc");
            query.setParameter("name", name);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByNameSortedDesc(String name){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where name = :name order by name desc");
            query.setParameter("name", name);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByPriceSortedDesc(int price, int delta) {
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product where price between :mDelta and :pDelta order by price desc");
            query.setParameter("mDelta", price - delta);
            query.setParameter("pDelta", price + delta);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

}
