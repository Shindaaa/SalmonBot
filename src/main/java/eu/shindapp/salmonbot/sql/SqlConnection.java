package eu.shindapp.salmonbot.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection {

    private static Connection conn;
    private Statement statement;
    private final String dbPath;

    public SqlConnection(String dbPath) {
        this.dbPath = dbPath;
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                conn.close();
                statement.close();
                System.out.println("Successfully disconnected to SQLite Database !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return conn != null;
    }

    public static Connection getConn() {
        return conn;
    }

}