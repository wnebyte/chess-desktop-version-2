package net.config;

public final class ConfigurationManager {

    private static Configuration configuration;

    public static void load() throws Exception {
        configuration = new ConfigurationLoader().call();
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}
