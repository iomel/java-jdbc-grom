package lesson1_2;

import java.sql.*;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public ResultSet getAllProducts() {
        ResultSet resultSet = null;
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM PRODUCT");

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductsByPrice(){
        ResultSet resultSet = null;
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE <=100");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductsByDescription(){
        ResultSet resultSet = null;
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE LENGTH(DESCRIPTION)>50");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return resultSet;
    }

    public void saveProduct(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("INSERT INTO PRODUCT VALUES (999, 'toy', 'for children', 60)");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
    public void deleteProducts(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("DELETE FROM PRODUCT WHERE NAME='toy'");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void increasePrice() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("UPDATE PRODUCT SET PRICE = PRICE + 100  WHERE PRICE < 970");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void changeDescription(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE LENGTH(DESCRIPTION)>100");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String description = resultSet.getString(3);
                description = description.substring(0, description.lastIndexOf("."));
                PreparedStatement p_statement = connection.prepareStatement("UPDATE PRODUCT SET DESCRIPTION = ? WHERE ID = ?");
                p_statement.setString(1, description);
                p_statement.setLong(1, id);
                p_statement.execute();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

}
