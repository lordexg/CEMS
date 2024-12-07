package com.sage.cems.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileManager {
    /*
    * This function return list of records with the searched keyword
    * The record represented as a Map the key is the column header and the value is the data cell
    * */
    List<Map<ColumnName, String>> getRows(TableName tableName, Object keyWord) throws IOException;

    // This Method return all the records in a given table as a list of maps
    List<Map<ColumnName, String>> getAllRows(TableName tableName) throws IOException;

    // This function update the first record that has the searched keyword with the new record
    void updateRow(TableName tableName, Map<ColumnName, String> row) throws IOException;

    // This function insert new raw into the specified table
    void insertRow(TableName tableName, Map<ColumnName, String> newRow) throws IOException;

    // This function delete the first record has the passed keyword
    void deleteRow(TableName tableName, Map<ColumnName, String> row) throws IOException;

    Map<ColumnName, String> getLastRow(TableName tableName) throws IOException;
}