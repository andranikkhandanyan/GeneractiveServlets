package am.aca.generactive.mock;

import am.aca.generactive.model.Configuration;
import am.aca.generactive.model.Resolution;

import java.util.Random;

public final class ConfigurationMock {

    public static Configuration getConfiguration() {
        return new Configuration(Resolution.values()[new Random().nextInt(Resolution.values().length - 1)]);
    }

    private ConfigurationMock() {

    }
}
