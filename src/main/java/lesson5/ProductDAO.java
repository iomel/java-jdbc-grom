package lesson5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;

    public ProductDAO() {
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    public void closeCommection(){
        factory.close();
    }
    // CREATE
    public void addProduct(long id, String name, String description, Integer price){
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new Product(id, name, description, price));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
    // READ
    public void listProducts( ){
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List products = session.createQuery("from Product").list();
            for (Product product : (List<Product>)products){
                System.out.print("ID::" + product.getId());
                System.out.print("  Name: " + product.getName());
                System.out.print("  Description: " + product.getDescription());
                System.out.println("  Price: " + product.getPrice());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    // DELETE
    public void deleteById(long id){
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Product product = new Product();
            product.setId(id);
            session.delete(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // UPDATE
    public void updateProduct(Product product){
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.merge(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
