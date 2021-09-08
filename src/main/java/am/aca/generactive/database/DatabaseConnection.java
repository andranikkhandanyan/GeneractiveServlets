package am.aca.generactive.database;

import am.aca.generactive.util.DatabaseConfigurationUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    public static Connection initializeConnection() throws SQLException {
        // Initialize all the information regarding
        // Database Connection
        Properties props = DatabaseConfigurationUtil.getConnectionProperties();
        // Get each property value.
        String dbDriverClass = props.getProperty("db.driver.class");
        String dbConnUrl = props.getProperty("db.url");
        String dbUsername = props.getProperty("db.username");
        String dbPassword = props.getProperty("db.password");

        // The forName() method of Class class is used to register the driver class.
        // This method is used to dynamically load the driver class.
        try {
            Class.forName(dbDriverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // As our application is heavily depends on the database connection,
            // we want it to be terminated if driver class has not been found.
            System.exit(1);
        }
        return DriverManager.getConnection(dbConnUrl,
                dbUsername,
                dbPassword);
    }
}
