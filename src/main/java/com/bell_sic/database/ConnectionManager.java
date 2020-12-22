package com.bell_sic.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {
    Connection getConnection() throws SQLException;
    Connection getPooledConnection() throws SQLException;
}
