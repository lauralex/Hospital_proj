package com.bell_sic.database;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class MariaDBConnectionManager implements ConnectionManager {
    // START OF DATABASE PARAMETERS SECTION
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String SERVER_NAME = "localhost";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE = "bellia_alessandro";
    // END OF DATABASE PARAMETERS SECTION

    private static Connection CONNECTION;
    private static PooledConnection POOLED_CONNECTION;

    private static MariaDBConnectionManager instance;

    private MariaDBConnectionManager() throws SQLException {
        MariaDbDataSource mariaDbDataSource = new MariaDbDataSource(SERVER_NAME, Integer.parseInt(PORT_NUMBER), DATABASE);
        CONNECTION = mariaDbDataSource.getConnection(USER, PASSWORD);
        POOLED_CONNECTION = mariaDbDataSource.getPooledConnection(USER, PASSWORD);
    }

    public static MariaDBConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new MariaDBConnectionManager();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return CONNECTION;
    }

    @Override
    public PooledConnection getPooledConnection() throws SQLException {
        return POOLED_CONNECTION;
    }
}
