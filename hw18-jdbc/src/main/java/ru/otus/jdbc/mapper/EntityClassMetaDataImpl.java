package ru.otus.jdbc.mapper;


import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> javaClass;

    public EntityClassMetaDataImpl(Class<T> javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public String getName() {
        return javaClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return (Constructor<T>) Arrays.stream(javaClass.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow();
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(javaClass.getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getAnnotation(Id.class)))
                .findAny().orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(javaClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(javaClass.getDeclaredFields())
                .filter(field -> Objects.isNull(field.getAnnotation(Id.class)))
                .toList();
    }
}
