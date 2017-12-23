package lesson4.file_storage.dao;

import lesson4.file_storage.model.File;

import java.sql.*;
import java.util.ArrayList;

public class FileDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public File save (File file) throws Exception{
        validate(file);
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)")) {
            statement.setLong(1, file.getId());
            statement.setString(2, file.getName());
            statement.setString(3,file.getFormat());
            statement.setLong(4, file.getSize());
            statement.setLong(5, file.getStorageId());

            int result = statement.executeUpdate();
            System.out.println("Save was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return file;
    }

    public File update (File file) throws Exception {
        validate(file);
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE FILES SET ?, ?, ?, ?, ? WHERE FILE_ID = ?")) {
            statement.setLong(1, file.getId());
            statement.setString(2, file.getName());
            statement.setString(3,file.getFormat());
            statement.setLong(4, file.getSize());
            statement.setLong(5, file.getStorageId());

            statement.setLong(6, file.getId());

            int result = statement.executeUpdate();

            System.out.println("Update was finished with result " + result);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return file;
    }

    public void delete (long id){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM FILES WHERE FILE_ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public File findById(long id){
        File foundObject = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM FILES WHERE FILE_ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            foundObject = getObject(result);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return foundObject;
    }

    public File[] findByStorageId(long id){
        ArrayList<File> filesList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next())
                filesList.add(getObject(result));
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        File[] files = new File[filesList.size()];
        return filesList.toArray(files);
    }

    private File getObject(ResultSet result) throws SQLException{
        long fileId = result.getLong(1);
        String name = result.getString(2);
        String format = result.getString(3);
        long size = result.getLong(4);
        long storageId = result.getLong(5);

        File foundObject = new File(fileId, name, format, size);
        foundObject.setStorageId(storageId);
        return foundObject;
    }

    private void validate(File file) throws Exception {

    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
