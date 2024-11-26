package com.sage.cems.util;

import java.util.List;
import java.util.Map;

public class CEMSFileManager implements FileManager{

    @Override
    public List<Map<ColumnName, String>> getRow(TableName tableName, Object keyWord) {
        return List.of();
    }

    @Override
    public void updateRow(TableName tableName, Object keyWord, Map<ColumnName, String> newRow) {

    }

    @Override
    public void insertNewRow(TableName tableName, Map<ColumnName, String> newRow) {

    }

    @Override
    public void deleteRaw(TableName tableName, Object keyWord) {

    }
}
