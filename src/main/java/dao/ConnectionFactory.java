package dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.util.Properties;

public class ConnectionFactory implements AutoCloseable {

    //  Database credentials
    private static final String PROPERTIES_NAME = "hikari@docker.properties";
    private HikariDataSource hds;

    public ConnectionFactory() {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME));
            HikariConfig cfg = new HikariConfig(properties);
            hds = new HikariDataSource(cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HikariDataSource getHikariDataSource() {
        return hds;
    }

    @Override
    public void close() {
        hds.close();
    }
}
