package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            // Load file config.properties
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Không thể load config.properties: " + e.getMessage());
        }
    }

    // Lấy giá trị theo key
    public static String get(String key) {
        return properties.getProperty(key);
    }

}
