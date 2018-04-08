package lesson6.NSQL;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;

    public ProductDAO() {
        factory = new Configuration().configure().buildSessionFactory();
    }


    public Product findById(Long id) {
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery("SELECT * FROM Product WHERE id = :id ");
            query.setParameter("id", id);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    public List<Product> findByName(String name) {
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery("SELECT * FROM Product WHERE name = :name ");
            query.setParameter("name", name);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByContainedName(String name){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery("SELECT * FROM Product WHERE name LIKE :name ");
            query.setParameter("name", "%" + name + "%");
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByPrice(int price, int delta){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery("SELECT * FROM Product WHERE price BETWEEN :mDelta AND :pDelta");
            query.setParameter("mDelta", price - delta);
            query.setParameter("pDelta", price + delta);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByNameSortedAsc(String name){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery("SELECT * FROM Product WHERE name = :name ORDER BY name ASC");
            query.setParameter("name", name);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByNameSortedDesc(String name){
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery("SELECT * FROM Product WHERE name = :name ORDER BY name DESC");
            query.setParameter("name", name);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> findByPriceSortedDesc(int price, int delta) {
        List<Product> list = null;
        try (Session session = factory.openSession()) {
            SQLQuery query = session.createSQLQuery(
                    "SELECT * FROM Product WHERE price BETWEEN :mDelta AND :pDelta ORDER BY price DESC");
            query.setParameter("mDelta", price - delta);
            query.setParameter("pDelta", price + delta);
            query.addEntity(Product.class);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

}
