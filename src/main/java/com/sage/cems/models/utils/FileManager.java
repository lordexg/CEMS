package com.sage.cems.models.utils;

import java.util.List;
import java.util.Map;

public interface FileManager {
    /*
    * This function return list of records with the searched keyword
    * The record represent as a Map the key is the column header and the value is the data cell
    * */
    List<Map<String, String>> getRow(TableType tableType, Object keyWord);

    // This function update the first record that has the searched keyword with the new record
    void updateRow(TableType tableType, Object keyWord, Map<String, String> newRow);

    // This function insert new raw into the specified table
    void insertNewRow(TableType tableType, Map<String, String> newRow);

    // This function delete the first record has the passed keyword
    void deleteRaw(TableType tableType, Object keyWord);
}
