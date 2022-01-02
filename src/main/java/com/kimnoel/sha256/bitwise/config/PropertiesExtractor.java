package com.kimnoel.sha256.bitwise.config;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesExtractor {
    private static Properties properties;

    static {
        properties = new Properties();
        URL url = PropertiesExtractor.class.getClassLoader().getResource("application.properties");
        try {
            properties.load(new FileInputStream(url.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }
}
