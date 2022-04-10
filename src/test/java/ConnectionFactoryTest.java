import dao.ConnectionFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

@Disabled
class ConnectionFactoryTest {
    @Test
    void propertiesTest() throws IOException {
        Properties props = new Properties();
        props.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("postgres@docker.properties"));
        String str = (String) props.get("driver-class-name");
        System.out.println(str);

    }

}