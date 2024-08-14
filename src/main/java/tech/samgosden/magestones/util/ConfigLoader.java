package tech.samgosden.magestones.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;


public class ConfigLoader {
    private static final Config config;

    static {
        File configFile = new File("config/magestones.conf");
        if (configFile.exists()) {
            config = ConfigFactory.parseFile(configFile);
        } else {
            //raise an exception
            throw new RuntimeException("Config file not found at config/magestones.conf");
        }
    }

    public static Config getConfig() {
        return config;
    }
}
