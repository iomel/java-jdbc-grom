package lesson6;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception {
        ProductDAO productDAO = new ProductDAO();

        Product product1 = new Product();
        product1.setName("Table");
        product1.setDescription("grey round table");
        product1.setPrice(70);

        Product product2 = new Product();
        product2.setName(" 222 Table");
        product2.setDescription("grey round table");
        product2.setPrice(170);

        Product product3 = new Product();
        product3.setName("Table 333");
        product3.setDescription("grey round table");
        product3.setPrice(710);

        Product product4 = new Product();
        product4.setName(" 444 Table");
        product4.setDescription("grey round table");
        product4.setPrice(701);

        List<Product> prodList = Arrays.asList(product2, product3, product4);

        productDAO.save(product1);
        productDAO.saveAll(prodList);
        System.out.println("Added");
        Thread.sleep(10000);

        product1.setDescription("UPDATED");
        product2.setDescription("UPDATED");
        product3.setDescription("UPDATED");
        product4.setDescription("UPDATED");
        productDAO.update(product1);
        productDAO.updateAll(prodList);
        System.out.println("Updated");
        Thread.sleep(10000);

        productDAO.delete(product1);
        productDAO.deleteAll(prodList);
        System.out.println("Deleted");

        System.out.println("END");
        System.exit(0);

    }
}
