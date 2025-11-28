package com.test.automation.challenge.api.utils;

import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Method;


public class APIUtils {

    public static String getDisplayName(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            DisplayName displayName = method.getAnnotation(DisplayName.class);
            return displayName != null ? displayName.value() : null;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Erro ao obter nome do cenário...");
        }
    }
}
