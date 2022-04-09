package eu.shindapp.salmonbot;

import eu.shindapp.salmonbot.client.SalmonClient;
import eu.shindapp.salmonbot.sql.SqlConnection;
import eu.shindapp.salmonbot.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.sql.Connection;


public class Main {

    private static Connection connection;
    private static SqlConnection sqlConnection;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        sqlConnection = new SqlConnection("src/main/resources/database.db");

        logger.info("Connecting to SQLite Database....");

        try {
            sqlConnection.connect();
            logger.info("Connected !");
        } catch (Exception ex) {
            logger.error("Unable to connect to Database! Shutting down SalmonBot.");
            ex.printStackTrace();
        }

        connection = SqlConnection.getConn();

        logger.info("Connecting to JDA...");

        try {
            new SalmonClient().init();
        } catch (LoginException | InterruptedException e) {
            logger.error("An error occur when trying to connect to JDA.");
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static SqlConnection getSqlConnection() {
        return sqlConnection;
    }
}
