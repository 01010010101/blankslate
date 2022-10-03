package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/test0?user=root&password=root";

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }
}