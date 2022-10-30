package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Transaction t = null;
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
        t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS `test0`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(255) NOT NULL,\n" +
                "  `lastName` VARCHAR(255) NOT NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
        query.executeUpdate();
        t.commit();
        System.out.println("The table was created");
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            t = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("DROP TABLE IF EXISTS test0.users");
        query.executeUpdate();
        t.commit();
        System.out.println("The table was erased");
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.saveOrUpdate(user);
            t.commit();
            System.out.println("successfully saved");
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.remove(user);
            t.commit();
            System.out.printf("User с id – %d исчез из реальности будто его никогда и не существовало\n живи с этим\n", id);
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            list = session.createQuery("SELECT a FROM User a", User.class).getResultList();
            t.commit();
            System.out.println("Gotcha");
            return list;
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
            return list;
        }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            String hql = "DELETE User";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            t.commit();
        System.out.println("The table is squeaky clean");
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }
}