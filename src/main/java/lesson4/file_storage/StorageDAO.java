package lesson4.file_storage;

import java.sql.*;

public class StorageDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.c88oq4boivpv.eu-west-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "AWS_Admin";

    public Storage save (Storage storage) throws Exception {
        try(Connection connection = getConnection()){
            if(findById(storage.getId()) != null)
                throw new Exception("Can't save storage - duplicated ID:" + storage.getId());
            save(connection, storage);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage() + " Storgae ID:" + storage.getId());
        }
        return storage;
    }

    private Storage save(Connection connection, Storage storage) throws SQLException {
        try(PreparedStatement statementStr = connection.prepareStatement("INSERT INTO STORAGES VALUES (?, ?, ?, ?)");
            PreparedStatement statementFile = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)")) {
            connection.setAutoCommit(false);
            storageStatementPrepare(statementStr, storage);
            if(storage.getFiles() != null)
                fileStatementPrepare(statementFile, storage.getFiles());
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e.getMessage() + "Couldn't be saved to Storage ID: " + storage.getId());
        }
        return storage;
    }

    public Storage update (Storage storage) throws Exception{
        try(Connection connection = getConnection()){
            update(connection, storage);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage() + "Couldn't be updated Storage ID: " + storage.getId());
        }
        return storage;
    }
    private Storage update (Connection connection, Storage storage) throws SQLException {
        try(PreparedStatement statementStr = connection.prepareStatement(
                    "UPDATE STORAGES SET STORAGE_ID = ?, FORMATS = ?, COUNTRY = ?, STORAGE_SIZE = ? WHERE STORAGE_ID = ?");
            PreparedStatement statementFile = connection.prepareStatement(
                    "UPDATE FILES " +
                            "SET FILE_ID = ?, FILE_NAME = ?, FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? " +
                            "WHERE FILE_ID = ?")) {
            connection.setAutoCommit(false);
            storageStatementPrepare(statementStr, storage);
            if(storage.getFiles() != null)
                fileStatementPrepare(statementFile, storage.getFiles());
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException( e.getMessage() + " Issue with storage ID: " + storage.getId());
        }
        return storage;
    }

    public void delete (long id) throws Exception{
        try(Connection connection = getConnection();
            PreparedStatement statementStr = connection.prepareStatement("DELETE FROM STORAGES WHERE STORAGE_ID = ?")){
            if(findById(id).getFiles().length > 0)
                throw new Exception("Storage is not empty - can't delete!");
            statementStr.setLong(1, id);
            int resultStr = statementStr.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException( e.getMessage() + "Issue with storage ID: " + id);
        }
    }

    public Storage findById(long id) throws Exception{
        Storage foundObject = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM STORAGES WHERE STORAGE_ID = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if(result.isBeforeFirst()) {
                String[] formats = result.getString(2).split(",");
                String country = result.getString(3);
                long size = result.getLong(4);
                foundObject = new Storage(id, formats, country, size);
                File[] files = new FileDAO().findByStorageId(id);
                foundObject.setFiles(files);
            }
        } catch (SQLException e) {
            throw new SQLException( e.getMessage() + "Issue with searching storage by ID: " + id);
        }
        return foundObject;
    }

    private void fileStatementPrepare(PreparedStatement statement, File[] files) throws SQLException {
        long fileID = 0;
        try {
            for (File file : files) {
                statement.setLong(1, file.getId());
                statement.setString(2, file.getName());
                statement.setString(3, file.getFormat());
                statement.setLong(4, file.getSize());
                statement.setLong(5, file.getId());
                fileID = file.getId();
                int resultFile = statement.executeUpdate();
            }
        } catch (SQLException e){
            throw new SQLException(e.getMessage() + " FileID: " + fileID );
        }
    }

    private void storageStatementPrepare(PreparedStatement statement, Storage storage) throws SQLException {
        try {
            statement.setLong(1, storage.getId());
            statement.setString(2, storage.getFormats());
            statement.setString(3,storage.getStorageCountry());
            statement.setLong(4, storage.getStorageSize());
            int result = statement.executeUpdate();
        } catch (SQLException e){
            throw new SQLException();
        }
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
