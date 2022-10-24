package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    StandardServiceRegistry ssr;
    Metadata meta;
    public UserDaoHibernateImpl() {
        ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        meta = new MetadataSources(ssr).getMetadataBuilder().build();
    }


    @Override
    public void createUsersTable() {
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("CREATE TABLE `test0`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(255) NOT NULL,\n" +
                "  `lastName` VARCHAR(255) NOT NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
        query.executeUpdate();
        t.commit();
        System.out.println("The table was created");

        factory.close();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("DROP TABLE test0.users");
        query.executeUpdate();
        t.commit();
        System.out.println("The table was erased");

        factory.close();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("INSERT INTO test0.users (name, lastName, age) VALUES (?, ?, ?)");
        query.setParameter(1, name);
        query.setParameter(2, lastName);
        query.setParameter(3, age);
        query.executeUpdate();
        t.commit();
        System.out.println("successfully saved");

        factory.close();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("DELETE FROM test0.users WHERE id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
        t.commit();
        System.out.printf("User с id – %d исчез из реальности будто его никогда и не существовало\n живи с этим\n", id);

        factory.close();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("SELECT * from test0.users");
/*        while (resultset.next()) {
            User user = new User();
            user.setName(resultset.getString("name"));
            user.setLastName(resultset.getString("lastName"));
            user.setAge((byte) resultset.getInt("age"));
            user.setId((long) resultset.getInt("id"));
            users.add(user);*/
        }
        query.executeUpdate();
        t.commit();
        return null;
        factory.close();
        session.close();
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("DELETE FROM test0.users");
        query.executeUpdate();
        t.commit();
        System.out.println("The table is squeaky clean");

        factory.close();
        session.close();
    }
}