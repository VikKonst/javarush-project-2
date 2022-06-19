package com.javarush.island.services;

import com.javarush.island.annotations.Injectable;
import com.javarush.island.species.animals.abstractItems.Animal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Injectable
public class AppService {
    private Properties properties;
    List<Class<? extends Animal>> allClassesExtendingAnimal = new ArrayList<>();

    public AppService() {
        initApplicationData();
    }

    public void loadProperties() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        properties = new Properties();
        try (InputStream input = classloader.getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            System.out.println("Can not load application.properties");
            ex.printStackTrace();
        }
    }

//    public static <T> T getProperty(String property, Class<T> tClass) {
//        return tClass.cast(properties.getProperty(property));
//    }
//
//    public static String getProperty(String property) {
//        return properties.getProperty(property);
//    }

    public int getIntProperty(String property) {
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

    public double getDblProperty(String property) {
        double result = 0;
        try {
            result = Double.parseDouble(properties.getProperty(property));
        } catch (NumberFormatException e) {
            System.out.println("Error while getting property " + property.toUpperCase());
        }
        return result;
    }

    public void initApplicationData() {
        loadProperties();
        allClassesExtendingAnimal.addAll(findAllClassesUsingClassLoader("com.javarush.island.species.animals.carnivorousAnimals"));
        allClassesExtendingAnimal.addAll(findAllClassesUsingClassLoader("com.javarush.island.species.animals.herbivorousAnimals"));
    }

    public List<Class<? extends Animal>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toList());
        } else throw new RuntimeException("Can not load animal classes in package " + packageName);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Animal> getClass(String className, String packageName) {
        try {
            Class<?> c = Class.forName(packageName + "."
                        + className.substring(0, className.lastIndexOf('.')));
            if (Animal.class.isAssignableFrom(c)) {
                return (Class<? extends Animal>) c;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("blah");
        }
        return null;
    }

    public List<Class<? extends Animal>> getAllClassesExtendingAnimal() {
        return allClassesExtendingAnimal;
    }
}
