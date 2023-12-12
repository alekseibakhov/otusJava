package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class TestFramework {
    private static int totalTests = 0;
    private static int successfulTests = 0;
    private static int failedTests = 0;
    static final Logger log = LoggerFactory.getLogger(TestFramework.class);

    public static void runTests(String className) throws Exception {
        Class<?> testClass = Class.forName(className);

        List<Method> beforeMethods = Helper.getAnnotatedMethods(testClass, Before.class);
        List<Method> testMethods = Helper.getAnnotatedMethods(testClass, Test.class);
        List<Method> afterMethods = Helper.getAnnotatedMethods(testClass, After.class);


        startTests(testMethods, testClass, beforeMethods, afterMethods);
        showStatistics();
    }

    private static void startTests(List<Method> testMethods, Class<?> testClass, List<Method> beforeMethods, List<Method> afterMethods) throws Exception {
        for (Method testMethod : testMethods) {

            Object testObject = testClass.getDeclaredConstructor().newInstance();
            totalTests++;

            try {
                invokeMethods(beforeMethods, testObject);
                testMethod.invoke(testObject);
                invokeMethods(afterMethods, testObject);
                successfulTests++;
            } catch (Exception e) {
                failedTests++;
                invokeMethods(afterMethods, testObject);
            }
        }
    }

    private static void showStatistics() {
        log.info("Test statistics:");
        log.info("Total {}, Successful{}, Failed{}", totalTests, successfulTests, failedTests);
    }

    private static void invokeMethods(List<Method> methods, Object object) throws Exception {
        for (Method method : methods) {
            method.invoke(object);
        }
    }

}
