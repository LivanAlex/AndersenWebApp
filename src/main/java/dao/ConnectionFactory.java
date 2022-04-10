package dao;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    //  Database credentials
    static final String DB_URL;
    static final String USER;
    static final String PASS;
    static final String DRIVER;
    static final String PROPERTIES_NAME;

    static{
        PROPERTIES_NAME = "postgres@docker.properties";
        Properties properties = new Properties();
        try {
            properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DB_URL = properties.getProperty("url");
        USER = properties.getProperty("username");
        PASS = properties.getProperty("password");
        DRIVER = properties.getProperty("driver-class-name");
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
