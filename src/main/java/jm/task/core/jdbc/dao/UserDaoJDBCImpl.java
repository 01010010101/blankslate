package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Код написан на примере кода от ментора.
 *
 * "а зачем так усложнять ?  Вот тебе пример где все проще , сделать так же остальные
 *
 *
 *
 *
 *
 *
 *
 * @Override
 *        public void removeUserById(long id) throws SQLException {
 *  	try (Statement statement = connection.createStatement()) {
 *  	connection.setAutoCommit(false);
 *  	statement.executeUpdate(ЗАПРОС);
 *
 *  	connection.commit();
 *    } catch (SQLException e) {
 *  	e.printStackTrace();
 *  	connection.rollback();
 *    } finally {
 *  	connection.setAutoCommit(true);
 *    }
 *    }"
 */


public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    {
        try {
            connection = Util.createConnection();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            statement.execute("CREATE TABLE `test0`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(255) NOT NULL,\n" +
                    "  `lastName` VARCHAR(255) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.fillInStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        System.out.println("Таблица поднялась");
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            statement.execute("DROP TABLE test0.users");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.fillInStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        System.out.println("Таблица упала");
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Statement statement = connection.createStatement();
             PreparedStatement prepared = connection.prepareStatement(
                     "INSERT INTO test0.users (name, lastName, age) VALUES (?, ?, ?)"
             )) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            prepared.setString(1, name);
            prepared.setString(2, lastName);
            prepared.setByte(3, age);
            prepared.execute();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            connection.rollback();
            e.fillInStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (Statement statement = connection.createStatement();
             PreparedStatement prepared = connection.prepareStatement(
                     "DELETE FROM test0.users WHERE id = ?"
             )) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            prepared.setLong(1, id);
            prepared.execute();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.printf("User с id – %d исчез из реальности будто его никогда и не существовало\n живи с этим\n", id);
        } catch (SQLException e) {
            connection.rollback();
            e.fillInStackTrace();
        }  finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
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

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute("START TRANSACTION");
            statement.execute("DELETE FROM test0.users");
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback();
            e.fillInStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        System.out.println("Ты чудовище!");
    }
}