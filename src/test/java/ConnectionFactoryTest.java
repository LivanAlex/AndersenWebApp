import com.zaxxer.hikari.HikariDataSource;
import dao.ConnectionFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Disabled
class ConnectionFactoryTest {
    @Test
    void propertiesTest() throws IOException, SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        HikariDataSource hds = factory.getHikariDataSource();
        Connection connection = hds.getConnection();
        System.out.println(connection);

    }

}