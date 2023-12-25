package ru.otus;

import ru.otus.hw.Ioc;
import ru.otus.hw.TestLoggingInterface;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface myClass = Ioc.createInstance();
        myClass.calculation(1, "Success with @Log annotation");
        myClass.calculation(1, 1);
        myClass.calculation(1);
    }
}
