package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private Properties properties;

    public Configuration(String configFilePath) {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getDataLibraryChoice() {
        return properties.getProperty("data.library");
    }
}
