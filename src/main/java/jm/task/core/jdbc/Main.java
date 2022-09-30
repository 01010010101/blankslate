package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Alla", "Pugacheva", (byte)107);
        service.saveUser("Vasya", "Pupkeen", (byte)12);
        service.saveUser("Goro", "Majima", (byte)55);
        service.saveUser("Monkey", "Nonhuman", (byte)5);
        List<User> list = service.getAllUsers();
        for (User user : list) {
            System.out.println(user.toString());
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
