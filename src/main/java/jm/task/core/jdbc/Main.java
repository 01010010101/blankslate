package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
/*      UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Alla", "Pugacheva", (byte) 107);
        service.saveUser("Vasya", "Pupkeen", (byte) 12);
        service.saveUser("Goro", "Majima", (byte) 55);
        service.saveUser("Monkey", "Nonhuman", (byte) 5);
        List<User> list = service.getAllUsers();
        for (User user : list) {
            System.out.println(user.toString());
        }*/
        //hibernateTestRun();
        //service.cleanUsersTable();
        //service.dropUsersTable();
    }

    private static void hibernateTestRun() {
        //Create typesafe ServiceRegistry object
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        User user = new User();

        user.setId(101L);
        user.setName("Milfa");
        user.setLastName("Lolita");
        user.setAge((byte) 15);

        session.save(user);
        t.commit();
        System.out.println("successfully saved");
        factory.close();
        session.close();
    }
}