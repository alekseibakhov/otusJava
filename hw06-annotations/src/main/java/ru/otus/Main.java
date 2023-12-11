package ru.otus;

import ru.otus.test.ExampleTestClass;
import ru.otus.test.TestFramework;

public class Main {
    public static void main(String[] args) throws Exception {
        TestFramework.runTests(ExampleTestClass.class.getName());
    }
}
