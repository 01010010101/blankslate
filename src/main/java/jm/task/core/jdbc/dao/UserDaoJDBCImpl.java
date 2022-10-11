package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.createConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            Boolean b0 = statement.execute("CREATE TABLE `test0`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(255) NOT NULL,\n" +
                    "  `lastName` VARCHAR(255) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
            if (b0) {
                statement.execute("COMMIT");
            } else {
                statement.execute("ROLLBACK");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        System.out.println("Таблица поднялась");
    }

    public void dropUsersTable() {
        try (Connection connection = Util.createConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            Boolean b0 = statement.execute("DROP TABLE test0.users");
            if (b0) {
                statement.execute("COMMIT");
            } else {
                statement.execute("ROLLBACK");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        System.out.println("Таблица упала");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.createConnection();
             Statement statement = connection.createStatement();
             PreparedStatement prepared = connection.prepareStatement(
                     "INSERT INTO test0.users (name, lastName, age) VALUES (?, ?, ?)"
             )) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            prepared.setString(1, name);
            prepared.setString(2, lastName);
            prepared.setByte(3, age);
            Boolean b0 = prepared.execute();
            if (b0) {
                statement.execute("COMMIT");
            } else {
                statement.execute("ROLLBACK");
            }
            connection.setAutoCommit(true);
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.createConnection();
             Statement statement = connection.createStatement();
             PreparedStatement prepared = connection.prepareStatement(
                     "DELETE FROM test0.users WHERE id = ?"
             )) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            prepared.setLong(1, id);
            Boolean b0 = prepared.execute();
            if (b0) {
                statement.execute("COMMIT");
            } else {
                statement.execute("ROLLBACK");
            }
            connection.setAutoCommit(true);
            System.out.printf("User с id – %d исчез из реальности будто его никогда и не существовало\n живи с этим\n", id);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.createConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultset = statement.executeQuery("SELECT * from test0.users");
            while (resultset.next()) {
                User user = new User();
                user.setName(resultset.getString("name"));
                user.setLastName(resultset.getString("lastName"));
                user.setAge((byte) resultset.getInt("age"));
                user.setId((long) resultset.getInt("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.createConnection(); Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            Boolean b0 = statement.execute("DELETE FROM test0.users");
            if (b0) {
                statement.execute("COMMIT");
            } else {
                statement.execute("ROLLBACK");
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        System.out.println("Ты чудовище!");
    }
}