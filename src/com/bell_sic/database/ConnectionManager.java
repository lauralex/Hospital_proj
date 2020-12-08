package com.bell_sic.database;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {
    /**
     * @return An instance of the Connection class to the database configured in the Manager class.
     * @throws SQLException If the connection failed.
     */
    Connection getConnection() throws SQLException;

    PooledConnection getPooledConnection() throws SQLException;
}
