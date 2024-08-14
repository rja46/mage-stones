package tech.samgosden.magestones.util;

import com.typesafe.config.Config;

import java.io.File;

public class ConfigHandler {
    public static Config config;
    public static void initialize(){
        //check to see if a config file has been written
        File configFile = new File("config/magestones.conf");
        if (!configFile.exists()) {
            ConfigWriter.writeDefaultConfig();
        }
        //load the config
        config = ConfigLoader.getConfig();
    }
}
