package tech.samgosden.magestones.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import tech.samgosden.magestones.MageStones;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigWriter {
    public static void writeDefaultConfig() {
        Config defaultConfig = ConfigFactory.parseResources("config/magestones.conf");
        File configFile = new File("config/magestones.conf");
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(defaultConfig.root().render(ConfigRenderOptions.defaults()));
                System.out.println("Default configuration file created at " + configFile.getAbsolutePath());
            } catch (IOException e) {
                MageStones.LOGGER.warn("Failed to write new file!");
            }
        }
    }
}
