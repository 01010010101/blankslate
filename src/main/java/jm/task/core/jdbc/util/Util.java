package jm.task.core.jdbc.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util implements Closeable {
    // реализуйте настройку соеденения с БД
    private Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test0?user=root&password=root");
    private Statement statement = connection.createStatement();

    public Util() throws SQLException {
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
