package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(BD_URL,BD_USERNAME,BD_PASSWORD);
    }

    private static final String BD_URL = "jdbc:mysql://localhost:3306/users";
    private static final String BD_USERNAME = "root";
    private static final String BD_PASSWORD = "root";
    private static final String BD_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String BD_DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, BD_DRIVER);
        properties.put(Environment.URL, BD_URL);
        properties.put(Environment.USER, BD_USERNAME);
        properties.put(Environment.PASS, BD_PASSWORD);
        properties.put(Environment.DIALECT, BD_DIALECT);
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        return new Configuration().setProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
    }


}

