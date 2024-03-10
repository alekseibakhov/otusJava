package ru.otus.appcontainer.api;

import java.lang.reflect.Method;

public class BeanConfig implements Comparable<BeanConfig> {
    private final int order;
    private final String methodName;
    private final Method method;

    public BeanConfig(int order, String methodName, Method method) {
        this.order = order;
        this.methodName = methodName;
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getMethod() {
        return method;
    }



    @Override
    public int compareTo(BeanConfig o) {
        return Integer.compare(this.order, o.order);
    }
}
