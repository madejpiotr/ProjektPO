import java.sql.*;

public class Database {
    private static Database db = new Database();
    private final String address = "jdbc:mysql://localhost/ProjektPO?serverTimezone=UTC";
    private Connection connection = null;
//    private PreparedStatement statement;
//    private ResultSet result;

    public Connection getConnection() {
        return connection;
    }

    public boolean Connect() {
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            connection = DriverManager.getConnection(address, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return isConnected();
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void Disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database getDB() {
        return db;
    }

}
