package lesson4;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        TransactionDemo td = new TransactionDemo();
        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product(231,"prod1", "very good prod1", 11);
        Product product2 = new Product(242,"prod2", "very good prod2", 111);
        Product product3 = new Product(242,"prod3", "very good prod3", 113);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        td.save(products);
    }

}
