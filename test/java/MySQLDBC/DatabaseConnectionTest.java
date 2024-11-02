package MySQLDBC;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    public void testInitializeDatabase() throws Exception {
        Connection connection = DatabaseConnection.initializeDatabase();
        assertNotNull(connection);
        connection.close();
    }
}