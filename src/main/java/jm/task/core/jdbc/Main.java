package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    private final static String URL = "jdbc:mysql://localhost:3306/test0";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";


    public static void main(String[] args) {
        //addUser("Alla","Pugacheva", (byte)256);
        //receiveUsers();
        //eraseUserById(13L);
        receiveUsers();
        //exterminateTable("bagOfDicks");
        killAllHumans();
        receiveUsers();
    }

    public static void addUser(String name, String lastName, Byte age) {
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

    public static void eraseUserById(Long id) {
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

    public static ArrayList<User> receiveUsers() {
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

    public static void createTable(String tableName) {

        try (Connection connection3 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
             Statement statement = connection3.createStatement()) {
            statement.execute(String.format ("CREATE TABLE %s (" +
                    "    id int," +
                    "    name varchar(255)," +
                    "    lastName varchar(255)," +
                    "   age int" +
                    ");", tableName));
            statement.execute("COMMIT");
            System.out.printf("Таблица – %s создана\n", tableName);

        } catch (SQLException e) {
            e.fillInStackTrace();

        }

    }

    public static void exterminateTable(String tableName) {

        try (Connection connection4 = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test0?user=root&password=root");
             Statement statement = connection4.createStatement()) {
            statement.execute(String.format ("DROP TABLE %s", tableName));
            statement.execute("COMMIT");
            System.out.printf("Таблица – %s упала\n и пропала\n насовсем\n", tableName);

        } catch (SQLException e) {
            e.fillInStackTrace();

        }

    }

    public static void killAllHumans() {
        ArrayList<User> auschwitz = receiveUsers();
        for (User user : auschwitz) {

            eraseUserById(user.getId());

        }
        System.out.println("Ты чудовище!");
    }

}
