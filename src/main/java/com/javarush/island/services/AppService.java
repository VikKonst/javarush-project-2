package com.javarush.island.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppService {

    private static Properties properties;

    public static void loadProperties() {
        System.out.println("Start loading properties...");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        properties = new Properties();
        try (InputStream input = classloader.getResourceAsStream("application.properties")) {
            properties.load(input);
            System.out.println("Properties loaded..");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static <T> T getProperty(String property, Class<T> tClass) {
        return tClass.cast(properties.getProperty(property));
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    public static int getIntProperty(String property) {
        int result = 0;
        try {
            String res = properties.getProperty(property);
            if (res == null)
                return result;
            result = Integer.parseInt(properties.getProperty(property));
        } catch (NumberFormatException e) {
            System.out.println("Error while getting property " + property.toUpperCase());
        }
        return result;
    }

    public static double getDblProperty(String property) {
        double result = 0;
        try {
            result = Double.parseDouble(properties.getProperty(property));
        } catch (NumberFormatException e) {
            System.out.println("Error while getting property " + property.toUpperCase());
        }
        return result;
    }
}
