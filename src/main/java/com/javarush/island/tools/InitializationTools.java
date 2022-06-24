package com.javarush.island.tools;

import com.javarush.island.annotations.Injectable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InitializationTools {
    public static <T> T createClass(Class<T> tClass) {
        Constructor<?> constructor = tClass.getConstructors()[0];
        Object[] parameterTypes = new Object[constructor.getParameterTypes().length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (constructor.getParameterTypes()[i].getAnnotation(Injectable.class) != null) {
                System.out.println("Inject " + constructor.getParameterTypes()[i].getName() + " into " + tClass.getName());
                parameterTypes[i] = createClass(constructor.getParameterTypes()[i]);
            }
        }
        Object instance;
        try {
            instance = constructor.newInstance(parameterTypes);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            System.out.println("Can not initialize class " + tClass.getName() + ". Please, report this mistake " + e);
            throw new RuntimeException();
        }
        return tClass.cast(instance);
    }
}
