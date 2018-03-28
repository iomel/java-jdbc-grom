package lesson5;

public class Demo {
    public static void main(String[] args) {
        ProductDAO pdao = new ProductDAO();

        pdao.addProduct(11192,"Boy War", "Super car for 16Y", 925);
        pdao.listProducts();
        System.out.println("==============  DELETE =================");
        pdao.deleteById(1192);
        pdao.deleteById(1392);
        pdao.deleteById(1192);

        pdao.listProducts();
        System.out.println("==============  UPDATE =================");
        Product pr = new Product(11192L, "Huba - Buba", "Sexy Ball", 99);
        pdao.updateProduct(pr);
        pdao.listProducts();

        pdao.closeCommection();
    }
}
