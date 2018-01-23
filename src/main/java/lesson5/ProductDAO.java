package lesson5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;


public class ProductDAO {
    private static SessionFactory factory;

    public ProductDAO() {
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

    }

    // CREATE
    public Integer addProduct(String name, String description, Integer price){
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer productId = null;

        try {
            transaction = session.beginTransaction();
            Product product = new Product(1243L, name, description, price);
            productId = (Integer) session.save(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return productId;
    }
    // READ
    public void listProducts( ){
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List products = session.createQuery("FROM Product").list();
            for (Iterator iterator = products.iterator(); iterator.hasNext();){
                Product product = (Product) iterator.next();
                System.out.print("Name: " + product.getName());
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
    // UPDATE

}
