package lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";


    public Product save (Product product){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            statement.setLong(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(3,product.getDescription());
            statement.setInt(4, product.getPrice());
            int result = statement.executeUpdate();

            System.out.println("Save was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return product;
    }

    public Product update (Product product){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE PRODUCT SET ?, ?, ? WHERE ID = ?")) {
            statement.setString(1, product.getName());
            statement.setString(2,product.getDescription());
            statement.setInt(3, product.getPrice());
            statement.setLong(4, product.getId());
            int result = statement.executeUpdate();

            System.out.println("Update was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> getProducts (){
        ArrayList<Product> products = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
            while (resultSet.next()){
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                int price = resultSet.getInt(4);
                products.add(new Product(id, name, description, price));
            }
            return products;

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    public void delete (long id){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM PRODUCT WHERE ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


}
