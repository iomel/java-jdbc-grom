package lesson5;

import org.hibernate.Session;

public class FirstDemo {
    public static void main(String[] args) {
        Session session = new HibernateUtils().createSessionFactory().openSession();

        Product product = new Product();
        product.setId(99);
        product.setName("Table");
        product.setDescription("grey round table");
        product.setPrice(70);

        session.getTransaction().begin();
        session.save(product);
        session.getTransaction().commit();
        System.out.println("Done");
        session.close();
    }
}
