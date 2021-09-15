package am.aca.generactive.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConfigurationUtil {

    public static final String DATABASE_PROPERTIES_FILE = "hibernate.properties";

    /**
     * Loads properties from {@code hibernate.properties} file.
     * Each module should provide its {@code hibernate.properties} file with database configuration.
     * {@code DatabaseConfigurationUtil.class.getClassLoader().getResource()} will load file
     * from concrete module resources.
     * @return {@link Properties} instance with keys/values from the {@code hibernate.properties} file.
     */
    public static Properties getConnectionProperties() {
        return readProperties(DATABASE_PROPERTIES_FILE);
    }

    public static Properties readProperties(String file) {
        // Create Properties object.
        Properties props = new Properties();

        try {
            // Load jdbc related properties in above file.
            props.load(Objects.requireNonNull(DatabaseConfigurationUtil.class
                    .getClassLoader()
                    .getResource(file)).openStream());

            return props;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load db properties from: "
                    + file);
        }
    }
}
