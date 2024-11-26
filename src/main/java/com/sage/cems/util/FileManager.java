package com.sage.cems.util;

import java.util.List;
import java.util.Map;

public interface FileManager {
    /*
    * This function return list of records with the searched keyword
    * The record represented as a Map the key is the column header and the value is the data cell
    * */
    List<Map<ColumnName, String>> getRow(TableName tableName, Object keyWord);

    // This function update the first record that has the searched keyword with the new record
    void updateRow(TableName tableName, Object keyWord, Map<ColumnName, String> newRow);

    // This function insert new raw into the specified table
    void insertNewRow(TableName tableName, Map<ColumnName, String> newRow);

    // This function delete the first record has the passed keyword
    void deleteRaw(TableName tableName, Object keyWord);
}