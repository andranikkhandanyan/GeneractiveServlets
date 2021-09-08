package am.aca.generactive.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConfigurationUtil {

    public static final String DATABASE_PROPERTIES_FILE = "database.properties";

    /**
     * Loads properties from {@code database.properties} file.
     * Each module should provide its {@code database.properties} file with database configuration.
     * {@code DatabaseConfigurationUtil.class.getClassLoader().getResource()} will load file
     * from concrete module resources.
     * @return {@link Properties} instance with keys/values from the {@code database.properties} file.
     */
    public static Properties getConnectionProperties() {
        // Create Properties object.
        Properties props = new Properties();

        try {
            // Load jdbc related properties in above file.
            props.load(Objects.requireNonNull(DatabaseConfigurationUtil.class
                    .getClassLoader()
                    .getResource(DATABASE_PROPERTIES_FILE)).openStream());

            return props;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load db properties from: "
                    + DATABASE_PROPERTIES_FILE);
        }
    }
}
