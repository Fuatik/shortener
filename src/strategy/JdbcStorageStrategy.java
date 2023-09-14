package src.strategy;

import src.ExceptionHandler;

import java.sql.*;

public class JdbcStorageStrategy implements StorageStrategy{
    private final Connection connection;

    public JdbcStorageStrategy() throws SQLException {
        String url = "jdbc:h2:mem";
        String user = "sa";
        String pass = "sa";

        connection = DriverManager.getConnection(url, user, pass);
        String createTable = "CREATE TABLE test (key INTEGER PRIMARY KEY, value TEXT NOT NULL)";
        executeUpdate(createTable);
    }

    private void executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(sql);
    }

    private String getValurFromDb(Long key) {
        String query = "SELECT value FROM test WHERE key = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, key);
            boolean hasResult = statement.execute();
            if (!hasResult) return null;

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) return resultSet.getString("value");
        } catch (SQLException e) {
            ExceptionHandler.log(e);
        }
        return null;
    }

    private Long getKeyFromDb(String value) {
        String query = "SELECT key FROM test WHERE value = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            boolean hasResult = statement.execute();
            if (!hasResult) return null;

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) return resultSet.getLong("key");
        } catch (SQLException e) {
            ExceptionHandler.log(e);
        }
        return null;
    }


    @Override
    public boolean containsKey(Long key) {
        return getValurFromDb(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        return getKeyFromDb(value) != null;
    }

    @Override
    public void put(Long key, String value) {
        String sql = String.format("INSET INTO test VALUES (%s, '%s')", key.toString(), value);

        try {
            executeUpdate(sql);
        } catch (SQLException e) {
            ExceptionHandler.log(e);
        }
    }

    @Override
    public Long getKey(String value) {
        return getKeyFromDb(value);
    }

    @Override
    public String getValue(Long key) {
        return getValurFromDb(key);
    }
}
