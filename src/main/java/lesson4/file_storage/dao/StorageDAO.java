package lesson4.file_storage.dao;

import lesson4.file_storage.model.File;
import lesson4.file_storage.model.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StorageDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public Storage save (Storage storage) throws Exception {
        try(Connection connection = getConnection()){
            save(connection, storage);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return storage;
    }

    private Storage save(Connection connection, Storage storage) throws SQLException {
        try(PreparedStatement statementStr = connection.prepareStatement("INSERT INTO STORAGES VALUES (?, ?, ?, ?, ?)");
            PreparedStatement statementFile = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)")) {
            connection.setAutoCommit(false);
            statementStr.setLong(1, storage.getId());
            statementStr.setString(2, storage.getFormats());
            statementStr.setString(3,storage.getStorageCountry());
            statementStr.setLong(4, storage.getStorageSize());

            for (File file : storage.getFiles()){
                statementFile.setLong(1, file.getId());
                statementFile.setString(2, file.getName());
                statementFile.setString(3, file.getFormat());
                statementFile.setLong(4, file.getSize());
                statementFile.setLong(5,file.getId());
                int resultFile = statementFile.executeUpdate();
            }
            int result = statementStr.executeUpdate();

            System.out.println("Save was finished with result " + result);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
            connection.rollback();
        }
        return storage;
    }

    public Storage update (Storage storage) throws Exception{
        try(Connection connection = getConnection()){
            update(connection, storage);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return storage;
    }
    private Storage update (Connection connection, Storage storage) throws SQLException {
        try(PreparedStatement statementStr = connection.prepareStatement("UPDATE STORAGES SET ?, ?, ?, ? WHERE STORAGE_ID = ?");
            PreparedStatement statementFile = connection.prepareStatement("UPDATE FILES SET ?, ?, ?, ?, ? WHERE FILE_ID = ?")) {
            connection.setAutoCommit(false);
            statementStr.setLong(1, storage.getId());
            statementStr.setString(2, storage.getFormats());
            statementStr.setString(3,storage.getStorageCountry());
            statementStr.setLong(4, storage.getStorageSize());

            for (File file : storage.getFiles()){
                statementFile.setLong(1, file.getId());
                statementFile.setString(2, file.getName());
                statementFile.setString(3, file.getFormat());
                statementFile.setLong(4, file.getSize());
                statementFile.setLong(5,file.getId());
                int resultFile = statementFile.executeUpdate();
            }

            int result = statementStr.executeUpdate();
            System.out.println("Update was finished with result " + result);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
            connection.rollback();
        }
        return storage;
    }

    public List<Storage> update (List<Storage> storages) throws Exception{
        try(Connection connection = getConnection()){
            update(connection, storages);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return storages;
    }
    private List<Storage> update (Connection connection, List<Storage> storages) throws SQLException {
        try(PreparedStatement statementStr = connection.prepareStatement("UPDATE STORAGES SET ?, ?, ?, ? WHERE STORAGE_ID = ?");
            PreparedStatement statementFile = connection.prepareStatement("UPDATE FILES SET ?, ?, ?, ?, ? WHERE FILE_ID = ?")) {
            connection.setAutoCommit(false);
            for(Storage storage : storages) {
                statementStr.setLong(1, storage.getId());
                statementStr.setString(2, storage.getFormats());
                statementStr.setString(3, storage.getStorageCountry());
                statementStr.setLong(4, storage.getStorageSize());

                for (File file : storage.getFiles()) {
                    statementFile.setLong(1, file.getId());
                    statementFile.setString(2, file.getName());
                    statementFile.setString(3, file.getFormat());
                    statementFile.setLong(4, file.getSize());
                    statementFile.setLong(5, file.getId());
                    int resultFile = statementFile.executeUpdate();
                }
                int result = statementStr.executeUpdate();
                System.out.println("Update was finished with result " + result);
            }
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
            connection.rollback();
        }
        return storages;
    }

    public void delete (long id){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM STORAGES WHERE FILE_ID = ?")) {
            statement.setLong(1, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void delete (Connection connection, long id) throws SQLException{

        try(PreparedStatement statementStr = connection.prepareStatement("DELETE FROM STORAGES WHERE STORAGE_ID = ?");
            PreparedStatement statementFile = connection.prepareStatement("DELETE FROM FILES WHERE STORAGE_ID = ?")) {
            connection.setAutoCommit(false);
            statementStr.setLong(1, id);
            statementFile.setLong(1, id);
            int resultStr = statementStr.executeUpdate();
            int resultFile = statementFile.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
            connection.rollback();
        }
    }

    public Storage findById(long id){
        Storage foundObject = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM STORAGES WHERE STORAGE_ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            String[] formats = result.getString(2).split(",");
            String country = result.getString(3);
            long size = result.getLong(4);
            foundObject = new Storage(id, formats, country, size);
            File[] files = new FileDAO().findByStorageId(id);
            foundObject.setFiles(files);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return foundObject;
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
