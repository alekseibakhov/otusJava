package ru.otus.jdbc.mapper;

import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<?> entityClassMetaDataClient;
    private final HwCache<Long, T> myCache;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<?> entityClassMetaDataClient,
                            HwCache<Long, T> myCache) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaDataClient = entityClassMetaDataClient;
        this.myCache = myCache;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var obj = myCache.get(id);
        if (Objects.nonNull(obj)) {
            return Optional.of(obj);
        }
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    var result = createResult(rs);
                    myCache.put(id, result);
                    return result;
                }

                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        var query = entitySQLMetaData.getSelectAllSql();

        return dbExecutor
                .executeSelect(connection, query, Collections.emptyList(), rs -> {
                    var entityList = new ArrayList<T>();

                    try {
                        while (rs.next()) {
                            entityList.add(createResult(rs));
                        }

                        return entityList;
                    } catch (SQLException
                             | InstantiationException
                             | IllegalAccessException
                             | InvocationTargetException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        var query = entitySQLMetaData.getInsertSql();
        var fieldsToInsert = entityClassMetaDataClient.getFieldsWithoutId();

        var paramValues =
                fieldsToInsert.stream().map(field -> getFieldValue(field, client)).toList();

        var idValue = dbExecutor.executeStatement(connection, query, paramValues);

        var idField = entityClassMetaDataClient.getIdField();
        try {
            idField.setAccessible(true);
            idField.set(client, idValue);
            myCache.put(idValue, client);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        } finally {
            idField.setAccessible(false);
        }

        return idValue;
    }

    @Override
    public void update(Connection connection, T client) {
        var query = entitySQLMetaData.getInsertSql();
        var fieldsToInsert = entityClassMetaDataClient.getFieldsWithoutId();
        Object idValue;
        Field idField = entityClassMetaDataClient.getIdField();

        idValue = getFieldValue(idField, client);

        var paramValues =
                new ArrayList<>(fieldsToInsert.stream().map(f -> getFieldValue(f, client)).toList());

        paramValues.add(idValue);

        dbExecutor.executeStatement(connection, query, paramValues);
        myCache.put((long) idValue, client);
    }

    private T createResult(ResultSet rs) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var allFields = entityClassMetaDataClient.getAllFields();
        var constructor = entityClassMetaDataClient.getConstructor();

        var args = allFields.stream().map(f -> getArgForConstructor(rs, f)).toList();

        return (T) constructor.newInstance(args.toArray());
    }

    private Object getArgForConstructor(ResultSet rs, Field f) {
        try {
            return rs.getObject(f.getName());
        } catch (SQLException e) {
            throw new DataTemplateException(e);
        }
    }

    private static Object getFieldValue(Field field, Object obj) {
        Object idValue;
        try {
            field.setAccessible(true);
            idValue = field.get(obj);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        } finally {
            field.setAccessible(false);
        }
        return idValue;
    }
}
