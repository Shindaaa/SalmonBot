package eu.shindapp.salmonbot.sql;

import java.sql.Connection;

public class SqlRequest {

    private final Connection connection;

    public SqlRequest(Connection connection) {
        this.connection = connection;
    }

}
