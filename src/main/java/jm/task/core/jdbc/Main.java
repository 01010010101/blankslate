package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    private final static String URL = "jdbc:mysql://localhost:3306/test0";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";


    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

    }
}
