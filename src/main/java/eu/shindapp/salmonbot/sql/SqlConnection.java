package eu.shindapp.salmonbot.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    private static Connection conn;
    private final int port;
    private final String urlBase;
    private final String host;
    private final String database;
    private final String user;
    private final String password;

    public SqlConnection(String urlBase, String host, int port, String database, String user, String password) {
        this.urlBase = urlBase;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void connect() {
        try {
            if (conn != null && !conn.isClosed()) {
                return;
            }
            synchronized (this) {
                if (conn != null && !conn.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(this.urlBase + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&wait_timeout=86400&serverTimezone=CET", this.user, this.password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                conn.close();
                System.out.println("Successfully disconnected to SQL Database !");
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