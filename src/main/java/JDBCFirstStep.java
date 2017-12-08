import java.sql.*;

public class JDBCFirstStep {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public static void main(String[] args) {
//    1. DB Driver
//    2. Create connection
//    3. create query
//    4. execute query
//    5. work with result
//    6. close connection!

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + JDBC_DRIVER + " not found");
            }
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM ORDERS ")) {
                while (resultSet.next()) {
                    // TODO some activity
                    System.out.println(resultSet.toString());
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

}
