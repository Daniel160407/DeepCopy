package org.example;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class CopyUtils {

    public static <T> T deepCopy(T obj) throws IllegalAccessException, InstantiationException {
        Map<Object, Object> visited = new HashMap<>();
        return deepCopy(obj, visited);
    }

    private static <T> T deepCopy(T obj, Map<Object, Object> visited) throws IllegalAccessException, InstantiationException {
        if (obj == null) {
            return null;
        }

        if (visited.containsKey(obj)) {
            return (T) visited.get(obj);
        }

        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            int length = Array.getLength(obj);
            Object newArray = Array.newInstance(componentType, length);
            visited.put(obj, newArray);

            for (int i = 0; i < length; i++) {
                Array.set(newArray, i, deepCopy(Array.get(obj, i), visited));
            }

            return (T) newArray;
        }

        if (obj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) obj;
            Collection<Object> newCollection = (Collection<Object>) obj.getClass().newInstance();
            visited.put(obj, newCollection);

            for (Object item : collection) {
                newCollection.add(deepCopy(item, visited));
            }

            return (T) newCollection;
        }

        if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            Map<Object, Object> newMap = new HashMap<>();
            visited.put(obj, newMap);

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                newMap.put(deepCopy(entry.getKey(), visited), deepCopy(entry.getValue(), visited));
            }

            return (T) newMap;
        }

        if (isPrimitiveWrapper(clazz)) {
            return obj;
        }

        if (obj instanceof String) {
            return (T) new String((String) obj);
        }

        T newInstance = (T) clazz.newInstance();
        visited.put(obj, newInstance);

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAccessible() && !Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                field.set(newInstance, deepCopy(field.get(obj), visited));
                field.setAccessible(false); // Reset the accessibility to its original state
            } else {
                field.set(newInstance, deepCopy(field.get(obj), visited));
            }
        }

        return newInstance;
    }

    private static boolean isPrimitiveWrapper(Class<?> clazz) {
        return clazz == Boolean.class ||
                clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Float.class ||
                clazz == Double.class ||
                clazz == Character.class;
    }
}
