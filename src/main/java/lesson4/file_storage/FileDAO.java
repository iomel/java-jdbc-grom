package lesson4.file_storage;

import java.sql.*;
import java.util.ArrayList;

public class FileDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public File save (File file) throws Exception{

        if(findById(file.getId()) != null)
            throw new Exception("Can't save file - duplicated ID: " + file.getId());

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)")) {

            statement.setLong(1, file.getId());
            statement.setString(2, file.getName());
            statement.setString(3,file.getFormat());
            statement.setLong(4, file.getSize());
            statement.setLong(5, file.getStorageId());

            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue to save File ID: " + file.getId());
        }
        return file;
    }



    public File update (File file) throws Exception {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE FILES " +
                    "SET FILE_ID = ?, FILE_NAME = ?, FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? " +
                    "WHERE FILE_ID = ?")) {
            statement.setLong(1, file.getId());
            statement.setString(2, file.getName());
            statement.setString(3,file.getFormat());
            statement.setLong(4, file.getSize());
            statement.setLong(5, file.getStorageId());

            statement.setLong(6, file.getId());

            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Can't update file ID: " + file.getId());
        }

        return file;
    }

    public void update (File[] files) throws Exception {
        try(Connection connection = getConnection()){
            update(connection, files);
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Can't update files list!");
        }
    }

    private void update(Connection connection, File[] files) throws SQLException{
        long fileId = 0;
        try( PreparedStatement statement = connection.prepareStatement(
                "UPDATE FILES " +
                        "SET FILE_ID = ?, FILE_NAME = ?, FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? " +
                        "WHERE FILE_ID = ?")) {
            connection.setAutoCommit(false);
            for(File file : files) {
                statement.setLong(1, file.getId());
                statement.setString(2, file.getName());
                statement.setString(3, file.getFormat());
                statement.setLong(4, file.getSize());
                statement.setLong(5, file.getStorageId());
                statement.setLong(6, file.getId());
                fileId = file.getId();
                int result = statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw  new SQLException( e.getMessage() + "Issue to update File ID:" + fileId);
        }
    }

    public void delete (long id) throws SQLException{
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM FILES WHERE FILE_ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + " Issue with deleting file ID: " + id);
        }
    }

    public File findById(long id) throws Exception{
        File foundObject = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM FILES WHERE FILE_ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst())
                while (result.next()) {
                    long fileId = result.getLong(1);
                    String name = result.getString(2);
                    String format = result.getString(3);
                    long size = result.getLong(4);
                    long storageId = result.getLong(5);

                    foundObject = new File(fileId, name, format, size);
                    foundObject.setStorageId(storageId);
                }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Issues with searching file by ID: " + id);        }
        return foundObject;
    }

    public File[] findByStorageId(long id) throws SQLException{
        ArrayList<File> filesList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                long fileId = result.getLong(1);
                String name = result.getString(2);
                String format = result.getString(3);
                long size = result.getLong(4);
                long storageId = result.getLong(5);

                File foundObject = new File(fileId, name, format, size);
                foundObject.setStorageId(storageId);
                filesList.add(foundObject);
            }
        } catch (SQLException e) {
            throw  new SQLException( e.getMessage() + "Something went wrong with file from storage ID: " + id);
        }
        File[] files = new File[filesList.size()];
        return filesList.toArray(files);
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
