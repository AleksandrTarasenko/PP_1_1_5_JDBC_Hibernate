package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String createTable = """
                        CREATE TABLE IF NOT EXISTS users
                        (
                            id       BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name     VARCHAR(40) ,
                            lastName VARCHAR(40) ,
                            age      TINYINT     null
                        );""";
                session.createSQLQuery(createTable).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("The table was not created" + e.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String query = "DROP TABLE IF EXISTS users";
                session.createSQLQuery(query).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("The table was not deleted" + e.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = new User();
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                System.out.println("The user was not added" + e.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.load(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (Exception e) {
                System.out.println("The user was not deleted" + e.getMessage());
                transaction.rollback();
            }
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                String getAll = "SELECT user FROM User user";
                list = session.createQuery(getAll, User.class).getResultList();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("The users were not received" + e.getMessage());
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                System.out.println("The table was not cleared" + e.getMessage());
                transaction.rollback();
            }
        }
    }
}
