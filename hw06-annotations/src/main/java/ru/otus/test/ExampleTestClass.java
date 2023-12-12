package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class ExampleTestClass {
    private static final Logger log = LoggerFactory.getLogger(ExampleTestClass.class);

    @Before
    public void setup() {
        log.info("Before method\t {}", this.hashCode());
    }

    @Test
    public void testMethod1() {
        log.info("Test method 1\t {}", this.hashCode());
    }

    @Test
    public void testMethod2() {
        log.info("Test method 2\t {}", this.hashCode());
        throw new RuntimeException();
    }

    @After
    public void cleanup() {
        log.info("After method 1\t {}", this.hashCode());
    }
}
