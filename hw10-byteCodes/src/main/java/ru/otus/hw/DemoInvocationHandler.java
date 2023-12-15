package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

class DemoInvocationHandler implements InvocationHandler {
    private final TestLogging myClass;
    private static final Logger log = LoggerFactory.getLogger(DemoInvocationHandler.class);

    DemoInvocationHandler(TestLogging myClass) {
        this.myClass = myClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method = TestLogging.class.getMethod("calculation", method.getParameterTypes());
        if (method.isAnnotationPresent(Log.class)) {
            log.info("Executed method: {}, param: {}", method.getName(), Arrays.toString(args));
        }
        return method.invoke(myClass, args);
    }

}
