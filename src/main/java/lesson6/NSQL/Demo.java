package lesson6.NSQL;


public class Demo {
    public static void main(String[] args) throws Exception {
        ProductDAO productDAO = new ProductDAO();

        System.out.println(productDAO.findById(2L).toString());

        for (Product product : productDAO.findByName("Table"))
            System.out.println(product.toString());

        for (Product product : productDAO.findByContainedName(" Tabl"))
            System.out.println(product.toString());

        for (Product product : productDAO.findByPrice(100, 70))
            System.out.println(product.toString());

        System.out.println("END");
        System.exit(0);

    }
}
