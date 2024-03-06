package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.api.BeanConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        var beanConfigList = Arrays.stream(configClass.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(AppComponent.class)).map(method -> {
            var annotation = method.getAnnotation(AppComponent.class);
            return new BeanConfig(annotation.order(), annotation.name(), method);
        }).sorted().toList();


        Object configClassObj = getConfigClassObj(configClass);

        for (BeanConfig elem : beanConfigList) {
            if (appComponentsByName.get(elem.getMethodName()) != null) {
                throw new RuntimeException("Component %s is already exists.".formatted(elem.getMethodName()));
            }
            Object obj = createBean(elem.getMethod(), configClassObj);
            appComponentsByName.put(elem.getMethodName(), obj);
            appComponents.add(obj);
        }
    }

    private Object createBean(Method method, Object config) {
        var paramTypes = method.getParameterTypes();

        Object[] args = Arrays.stream(paramTypes).map(this::getBean).toArray();

        try {
            return method.invoke(config, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getBean(Class<?> clazz) {
        var beanList = appComponents.stream().filter(c -> c.getClass().equals(clazz) || hasInterfaceOf(c, clazz)).toList();
        if (beanList.size() > 1) {
            throw new RuntimeException("Class has more 1 bean with name ");
        }
        return beanList.get(0);
    }

    private boolean hasInterfaceOf(Object c, Class<?> clazz) {
        return Arrays.asList(c.getClass().getInterfaces()).contains(clazz);
    }

    private static Object getConfigClassObj(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) getBean(componentClass);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C c = (C) appComponentsByName.get(componentName);
        if (Objects.isNull(c)) {
            throw new RuntimeException("ComponentName %s is not exist".formatted(componentName));
        }
        return c;
    }
}
