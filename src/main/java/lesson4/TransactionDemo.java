package lesson4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDemo {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public void save (List<Product> products){
        try(Connection connection = getConnection()){
            connection.setAutoCommit(false);
            saveList(products, connection);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void saveList(List<Product> products, Connection connection) throws SQLException{
        long tempId = 0;
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            for(Product product : products) {
                statement.setLong(1, product.getId());
                statement.setString(2, product.getName());
                statement.setString(3, product.getDescription());
                statement.setInt(4, product.getPrice());
                tempId = product.getId();
                int result = statement.executeUpdate();
                System.out.println("Save was finished with result " + result);
            }
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e.getMessage() + " Product ID:" + tempId);
        }

    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
