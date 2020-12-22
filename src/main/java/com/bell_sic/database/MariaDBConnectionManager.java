package com.bell_sic.database;

import org.mariadb.jdbc.MariaDbDataSource;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MariaDBConnectionManager implements ConnectionManager {
    //region DATABASE CONNECTION PARAMETERS
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String SERVER_NAME = "localhost";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE = "bellia_alessandro";
    //endregion

    private static final DataSource MARIADB_DATASOURCE = new MariaDbDataSource(SERVER_NAME, Integer.parseInt(PORT_NUMBER), DATABASE);
    private static final DataSource MARIADB_POOL_DATASOURCE = new MariaDbPoolDataSource(SERVER_NAME, Integer.parseInt(PORT_NUMBER), DATABASE);

    private static class InstanceHolder {
        private static final MariaDBConnectionManager instance = new MariaDBConnectionManager();
    }

    public static MariaDBConnectionManager getInstance() {
        return InstanceHolder.instance;
    }

    private MariaDBConnectionManager() {
        //no instance
    }

    public Connection getConnection() throws SQLException {
        return MARIADB_DATASOURCE.getConnection();
    }

    public Connection getPooledConnection() throws SQLException {
        return MARIADB_POOL_DATASOURCE.getConnection();
    }
}
