package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {

        try (Connection connection3 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
             Statement statement = connection3.createStatement()) {
            statement.execute("CREATE TABLE `test0`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(255) NOT NULL,\n" +
                    "  `lastName` VARCHAR(255) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
            statement.execute("COMMIT");
            System.out.println("Таблица – users создана\n");

        } catch (SQLException e) {
            e.fillInStackTrace();

        }

    }

    public void dropUsersTable() {
        try (Connection connection4 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
             Statement statement = connection4.createStatement()) {
            statement.execute(String.format ("DROP TABLE test0.users"));
            statement.execute("COMMIT");
            System.out.printf("Таблица – users упала\n и пропала\n насовсем\n");
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection1 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
             Statement statement = connection1.createStatement()) {
            statement.execute(String.format ("INSERT INTO test0.users (name, lastName, age) VALUES ('%s', '%s', %d)", name, lastName, age));
            statement.execute("COMMIT");
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection2 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
             Statement statement = connection2.createStatement()) {
            statement.execute(String.format ("DELETE FROM test0.users WHERE id = %d", id));
            statement.execute("COMMIT");
            System.out.printf("User с id – %d исчез из реальности будто его никогда и не существовало\n живи с этим\n", id);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
            Statement statement = connection.createStatement()) {
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
        for (User user : users) {
            System.out.println(user.toString());
        }
        return users;
    }

    public void cleanUsersTable() {
        List<User> auschwitz = getAllUsers();
        for (User user : auschwitz) {
            removeUserById(user.getId());
        }
        System.out.println("Ты чудовище!");
    }

}

