package lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public List<Product> findProductsByPrice(int price, int delta){
        ArrayList<Product> products = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE PRICE >= ? AND =< ?")) {
            statement.setInt(1, price-delta);
            statement.setInt(2, price+delta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                products.add(createProduct(resultSet));
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> findProductsByName(String word) {
        ArrayList<Product> products = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE NAME LIKE %?%")) {
            statement.setString(1, word);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                products.add(createProduct(resultSet));
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> findProductsWithEmptyDescription() {
        ArrayList<Product> products = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE DESCRIPTION = ''");
            while (resultSet.next()){
                products.add(createProduct(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return products;
    }

    private Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String description = resultSet.getString(3);
        int price = resultSet.getInt(4);
        return new Product(id, name, description, price);
    }
}
