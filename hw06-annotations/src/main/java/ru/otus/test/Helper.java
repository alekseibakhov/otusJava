package ru.otus.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static List<Method> getAnnotatedMethods(Class<?> aClass, Class<? extends Annotation> annotationName) {
        Method[] methods = aClass.getDeclaredMethods();

        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(annotationName)).collect(Collectors.toList());
    }
}
