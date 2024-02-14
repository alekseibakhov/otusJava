package ru.otus.jdbc.mapper;


import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaDataClient;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from %s"
                .formatted(entityClassMetaDataClient.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from %s where %s = ?"
                .formatted(entityClassMetaDataClient.getName(), entityClassMetaDataClient.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        var tableName = entityClassMetaDataClient.getName();
        var fieldNamesWithoutId = entityClassMetaDataClient.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();

        var columnNames = String.join(", ", fieldNamesWithoutId);

        var countValues = fieldNamesWithoutId.stream().map(params -> "?").toList();
        var countParams = String.join(", ", countValues);

        return "insert into %s (%s) values (%s)".formatted(tableName, columnNames, countParams);
    }

    @Override
    public String getUpdateSql() {
        var idField = entityClassMetaDataClient.getIdField();

        var tableName = entityClassMetaDataClient.getName();
        var fieldNamesWithoutId = entityClassMetaDataClient.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();

        var idFieldName = idField.getName();

        var countValues = fieldNamesWithoutId.stream().map("%s = ?"::formatted).toList();
        var countParams = String.join(", ", countValues);

        return "update %s set %s where %s = ?".formatted(tableName, countParams, idFieldName);
    }
}
