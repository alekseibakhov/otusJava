package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

class DemoInvocationHandler implements InvocationHandler {
    private final TestLogging myClass;
    private final HashSet<Method> methods;
    private static final Logger log = LoggerFactory.getLogger(DemoInvocationHandler.class);

    DemoInvocationHandler(TestLogging myClass) {
        methods = new HashSet<>();
        this.myClass = myClass;
        createAnnotationLogMethods(this.myClass.getClass().getDeclaredMethods());

    }

    private void createAnnotationLogMethods(Method[] methodList) {
        for (Method method : methodList) {
            if (method.isAnnotationPresent(Log.class)) {
                methods.add(method);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method = TestLogging.class.getMethod(method.getName(), method.getParameterTypes());
        if (methods.contains(method)) {
            log.info("Executed method: {}, param: {}", method.getName(), Arrays.toString(args));
        }
        return method.invoke(myClass, args);
    }

}
