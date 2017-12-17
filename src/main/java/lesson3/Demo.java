package lesson3;

public class Demo {
    public static void main(String[] args) throws Exception{

//        ProductDAO productDAO = new ProductDAO();
        Solution solution = new Solution();

//        Product product1 = new Product(101, "joyy", "", 99);
//        Product product2 = new Product(102, "Cartoy", "description of toy", 111);
//        Product product3 = new Product(103, "toy young ", "", 90);
//        Product product4 = new Product(104, "toyy_jungle", "description of toy", 110);
//
//        productDAO.save(product1);
//        productDAO.save(product2);
//        productDAO.save(product3);
//        productDAO.save(product4);

        for (Product p : solution.findProductsByPrice(100, 10))
            System.out.println(p.toString());

        System.out.println("*************");
        for (Product p : solution.findProductsByName("toy"))
            System.out.println(p.toString());

//        System.out.println("*************");
//        for (Product p : solution.findProductsByName("oy yo"))
//            System.out.println(p.toString());

        System.out.println("*************");
        for (Product p : solution.findProductsWithEmptyDescription())
            System.out.println(p.toString());

        System.out.println("*************");

    }

}
