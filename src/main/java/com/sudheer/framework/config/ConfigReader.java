package com.sudheer.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties; any value can be overridden with -Dkey=value. */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }
    }

    private ConfigReader() { }

    public static String get(String key) {
        String override = System.getProperty(key);
        return override != null ? override : PROPS.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
