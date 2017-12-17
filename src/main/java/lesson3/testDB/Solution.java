package lesson3.testDB;

import java.sql.*;
import java.util.Date;

public class Solution {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + JDBC_DRIVER + " not found");
        }
    }

    public long testSavePerformance(){
        long start = new Date().getTime();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)")) {
            for (int i = 0; i<50000; i++) {
                statement.setLong(1, i);
                statement.setString(2, "test string to add");
                statement.setInt(3, i);
                int result = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
       return new Date().getTime() - start;
    }

    public long testDeleteByIdPerformance(){
        long start = new Date().getTime();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE ID = ?")) {
            for (int i = 0; i<50000; i++) {
                statement.setLong(1, i);
                int result = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return new Date().getTime() - start;
    }

    public long testDeletePerformance(){
        long start = new Date().getTime();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM TEST_SPEED")) {
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return new Date().getTime() - start;
    }

    public long testSelectByIdPerformance(){
        long start = new Date().getTime();
        for (int i = 0; i<50000; i++) {

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEST_SPEED WHERE ID = ?")) {
                statement.setLong(1, i);
                int result = statement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Something went wrong");
                e.printStackTrace();
            }
        }
        return new Date().getTime() - start;
    }

    public long testSelectPerformance(){
        long start = new Date().getTime();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEST_SPEED")) {
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return new Date().getTime() - start;
    }

    private Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
