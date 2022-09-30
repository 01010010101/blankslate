package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Util util = new Util()) {
            util.getStatement().execute("CREATE TABLE `test0`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(255) NOT NULL,\n" +
                    "  `lastName` VARCHAR(255) NOT NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
            util.getStatement().execute("COMMIT");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Util util = new Util();) {
            util.getStatement().execute("DROP TABLE test0.users");
            util.getStatement().execute("COMMIT");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Util util = new Util();) {
            util.getStatement().execute(String.format ("INSERT INTO test0.users (name, lastName, age) VALUES ('%s', '%s', %d)", name, lastName, age));
            util.getStatement().execute("COMMIT");
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Util util = new Util();) {
            util.getStatement().execute(String.format ("DELETE FROM test0.users WHERE id = %d", id));
            util.getStatement().execute("COMMIT");
            System.out.printf("User с id – %d исчез из реальности будто его никогда и не существовало\n живи с этим\n", id);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Util util = new Util();) {
            ResultSet resultset = util.getStatement().executeQuery("SELECT * from test0.users");
            while (resultset.next()) {
                User user = new User();
                user.setName(resultset.getString("name"));
                user.setLastName(resultset.getString("lastName"));
                user.setAge((byte) resultset.getInt("age"));
                user.setId((long) resultset.getInt("id"));
                users.add(user);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
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