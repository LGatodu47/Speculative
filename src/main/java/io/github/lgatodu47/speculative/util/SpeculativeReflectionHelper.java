package io.github.lgatodu47.speculative.util;

import cpw.mods.modlauncher.api.INameMappingService.Domain;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.minecraftforge.fml.util.ObfuscationReflectionHelper.remapName;

public class SpeculativeReflectionHelper {
    private static final Logger LOGGER = LogManager.getLogger("Speculative Reflection");
    private static final Map<String, Field> FIELD_CACHE = new HashMap<>();
    private static final Map<String, Field> STATIC_FIELD_CACHE = new HashMap<>();
    
    private static final Map<String, Method> METHOD_CACHE = new HashMap<>();
    private static final Map<String, Method> STATIC_METHOD_CACHE = new HashMap<>();

    public static <T, F> Optional<F> getStaticFieldValue(Class<T> clazz, String fieldName, Class<F> fieldType) {
        Field field;
        if(!STATIC_FIELD_CACHE.containsKey(fieldName)) {
            field = ObfuscationReflectionHelper.findField(clazz, fieldName);
            if(!Modifier.isStatic(field.getModifiers())) {
                throw new RuntimeException(new NoSuchFieldException("There is no static field '" + remapName(Domain.FIELD, fieldName) + "' in class '" + clazz.getSimpleName() + "'!"));
            }
            STATIC_FIELD_CACHE.put(fieldName, field);
        }
        else {
            field = STATIC_FIELD_CACHE.get(fieldName);
        }

        return getFieldValue(clazz, null, field, fieldName, fieldType);
    }

    public static <T, F> Optional<F> getFieldValue(Class<T> clazz, T instance, String fieldName, Class<F> fieldType) {
        Field field;
        if(!FIELD_CACHE.containsKey(fieldName)) {
            field = ObfuscationReflectionHelper.findField(clazz, fieldName);
            if(Modifier.isStatic(field.getModifiers())) {
                LOGGER.warn("Used SpeculativeReflectionHelper#getFieldValue for static field '{}' in class '{}'!", remapName(Domain.FIELD, fieldName), clazz.getSimpleName());
                STATIC_FIELD_CACHE.putIfAbsent(fieldName, field);
                return getStaticFieldValue(clazz, fieldName, fieldType);
            }
            FIELD_CACHE.put(fieldName, field);
        }
        else {
            field = FIELD_CACHE.get(fieldName);
        }

        return getFieldValue(clazz, instance, field, fieldName, fieldType);
    }

    private static <T, F> Optional<F> getFieldValue(Class<T> clazz, T instance, Field field, String fieldName, Class<F> fieldType) {
        try {
            Object ret = field.get(instance);
            if(ret != null) {
                if(fieldType.isInstance(ret)) {
                    return Optional.of(fieldType.cast(ret));
                }
                LOGGER.warn("Field '{}' in class '{}' returned an object with a different type than expected! Expected: '{}', Got: '{}'", remapName(Domain.FIELD, fieldName), clazz.getSimpleName(), fieldType.getSimpleName(), ret.getClass().getSimpleName());
            }
        } catch (IllegalAccessException e) {
            LOGGER.error(String.format("Could not access field '%s' in class '%s'!", remapName(Domain.FIELD, fieldName), clazz.getSimpleName()), e);
        }

        return Optional.empty();
    }

    public static <T, V> void setStaticFieldValue(Class<T> clazz, String fieldName, V value) {
        Field field;
        if(!STATIC_FIELD_CACHE.containsKey(fieldName)) {
            field = ObfuscationReflectionHelper.findField(clazz, fieldName);
            if(!Modifier.isStatic(field.getModifiers())) {
                throw new RuntimeException(new NoSuchFieldException("There is no static field '" + remapName(Domain.FIELD, fieldName) + "' in class '" + clazz.getSimpleName() + "'!"));
            }
            STATIC_FIELD_CACHE.put(fieldName, field);
        }
        else {
            field = STATIC_FIELD_CACHE.get(fieldName);
        }

        try {
            field.set(null, value);
        } catch (IllegalAccessException e) {
            LOGGER.error(String.format("Could not access field '%s' in class '%s'!", remapName(Domain.FIELD, fieldName), clazz.getSimpleName()), e);
        }
    }

    public static <T, V> void setFieldValue(Class<T> clazz, T instance, String fieldName, V value) {
        Field field;
        if(!FIELD_CACHE.containsKey(fieldName)) {
            field = ObfuscationReflectionHelper.findField(clazz, fieldName);
            if(Modifier.isStatic(field.getModifiers())) {
                LOGGER.warn("Used SpeculativeReflectionHelper#setFieldValue for static field '{}' in class '{}'!", remapName(Domain.FIELD, fieldName), clazz.getSimpleName());
                STATIC_FIELD_CACHE.putIfAbsent(fieldName, field);
                setStaticFieldValue(clazz, fieldName, value);
                return;
            }
            FIELD_CACHE.put(fieldName, field);
        }
        else {
            field = FIELD_CACHE.get(fieldName);
        }

        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            LOGGER.error(String.format("Could not access field '%s' in class '%s'!", remapName(Domain.FIELD, fieldName), clazz.getSimpleName()), e);
        }
    }

    public static <T> ReflectedMethod getStaticMethod(Class<T> clazz, String methodName, Class<?>... parameterTypes) {
        if(!STATIC_METHOD_CACHE.containsKey(methodName)) {
            Method method = ObfuscationReflectionHelper.findMethod(clazz, methodName, parameterTypes);
            if(!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException(new NoSuchMethodException("There is no static method '" + remapName(Domain.METHOD, methodName) + "' in class '" + clazz.getSimpleName() + "'!"));
            }
            STATIC_METHOD_CACHE.put(methodName, method);
        }

        return new ReflectedMethod(STATIC_METHOD_CACHE.get(methodName), null, methodName);
    }

    public static <T> ReflectedMethod getMethod(Class<T> clazz, T instance, String methodName, Class<?>... parameterTypes) {
        if(!METHOD_CACHE.containsKey(methodName)) {
            Method method = ObfuscationReflectionHelper.findMethod(clazz, methodName, parameterTypes);
            if(Modifier.isStatic(method.getModifiers())) {
                LOGGER.warn("Used SpeculativeReflectionHelper#getMethod for static method '{}' in class '{}'!", remapName(Domain.METHOD, methodName), clazz.getSimpleName());
                STATIC_METHOD_CACHE.putIfAbsent(methodName, method);
                return getStaticMethod(clazz, methodName, parameterTypes);
            }
            METHOD_CACHE.put(methodName, method);
        }

        return new ReflectedMethod(METHOD_CACHE.get(methodName), instance, methodName);
    }

    public static final class ReflectedMethod {
        private final Method method;
        private final Object instance;
        private final String name;

        private ReflectedMethod(Method method, Object instance, String methodName) {
            this.method = method;
            this.instance = instance;
            this.name = methodName;
        }

        public void invoke(Object... args) {
            try {
                method.invoke(instance, args);
            } catch (IllegalAccessException e) {
                LOGGER.error(errorFormat("Could not access method named '%s' in class '%s'!"), e);
            } catch (InvocationTargetException e) {
                LOGGER.error(errorFormat("Caught an exception when invoking method '%s' in class '%s'!"), e);
            }
        }

        public <R> Optional<R> invoke(Class<R> returnType, Object... args) {
            try {
                Object ret = method.invoke(instance, args);
                if(ret != null) {
                    if(returnType.isInstance(ret)) {
                        return Optional.of(returnType.cast(ret));
                    }
                    LOGGER.warn(errorFormat("Method '%s' in class '%s' returned an object with a different type than expected! Expected: '{}', Got: '{}'"), returnType.getSimpleName(), ret.getClass().getSimpleName());
                }
            } catch (IllegalAccessException e) {
                LOGGER.error(errorFormat("Could not access method named '%s' in class '%s'!"), e);
            } catch (InvocationTargetException e) {
                LOGGER.error(errorFormat("Caught an exception when invoking method '%s' in class '%s'!"), e);
            }

            return Optional.empty();
        }

        private String errorFormat(String str) {
            return String.format(str, remapName(Domain.METHOD, name), method.getDeclaringClass().getSimpleName());
        }
    }
}
