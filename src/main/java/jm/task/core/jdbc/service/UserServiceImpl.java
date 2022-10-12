package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {


    public UserServiceImpl() {
    }

    public void createUsersTable() {
        try {
            UserDao userDaoJDBC = new UserDaoJDBCImpl();
            userDaoJDBC.createUsersTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            UserDao userDaoJDBC = new UserDaoJDBCImpl();
            userDaoJDBC.dropUsersTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            UserDao userDaoJDBC = new UserDaoJDBCImpl();
            userDaoJDBC.saveUser(name, lastName, age);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            UserDao userDaoJDBC = new UserDaoJDBCImpl();
            userDaoJDBC.removeUserById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        UserDao userDaoJDBC = new UserDaoJDBCImpl();
        return userDaoJDBC.getAllUsers();
    }

    public void cleanUsersTable() {
        try {
            UserDao userDaoJDBC = new UserDaoJDBCImpl();
            userDaoJDBC.cleanUsersTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}