package am.aca.generactive.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionTest {
    public static Connection initializeConnection() throws ClassNotFoundException, SQLException {
        // Initialize all the information regarding
        // Database Connection
        String dbDriver = "org.postgresql.Driver";
        String dbURL = "jdbc:postgresql://localhost:5432/";
        // Database name to access
        String dbName = "generactive_aca";
        String dbUsername = "generactive_aca";
        String dbPassword = "123456";

        Properties properties = new Properties();
        properties.setProperty("user", dbUsername);
        properties.setProperty("password", dbPassword);

        // The forName() method of Class class is used to register the driver class.
        // This method is used to dynamically load the driver class.
        Class.forName(dbDriver);
//        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbName",
//                dbUsername,
//                dbPassword);
        Connection connection = DriverManager.getConnection(dbURL + dbName, properties);
        return connection;
    }
}
