package com.sage.cems.util;

import com.sage.cems.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CEMSFileManager implements FileManager{

    public CEMSFileManager() throws IOException {
        if (!isAllFilesExist())
            throw new IOException("Cannot access the files base");
    }

    @Override
    public List<Map<ColumnName, String>> getRows(TableName tableName, Object keyWord) {
        return List.of();
    }

    @Override
    public void updateRow(TableName tableName, Map<ColumnName, String> newRow) {

    }

    @Override
    public void insertRow(TableName tableName, Map<ColumnName, String> newRow) {
        switch (tableName) {
            case STUDENT -> fun(tableName, newRow);
            case LECTURER -> fun(tableName, newRow);
            case ACCOUNT -> fun(tableName, newRow);
            case COURSE -> fun(tableName, newRow);
            case ENROLLMENT -> fun(tableName, newRow);
            case EXAM -> fun(tableName, newRow);
            case SOLVED_EXAM -> fun(tableName, newRow);
            case QUESTION -> fun(tableName, newRow);
        }
    }

    @Override
    public void deleteRaw(TableName tableName, Object keyWord) {

    }
    
    
    // Helper Methods
    private boolean isAllFilesExist() {
        File dir = new File(Configuration.FILES_DIR);
        if (!dir.exists()) return false;

        File[] files = dir.listFiles();
        if (files == null) return false;

        List<String> filesNamesOnDisk = new ArrayList<>();
        for (File i : files) {
            filesNamesOnDisk.add(i.getName());
        }

        List<String> filesNames = new ArrayList<>();
        for (TableName i : TableName.values()) {
            filesNames.add(i.toString() + ".txt");
        }

        return new HashSet<>(filesNamesOnDisk).containsAll(filesNames);
    }
    
    private void fun(TableName tableName, Map<ColumnName, String> newRow) {
        StringBuilder rowToAppend = new StringBuilder();
        List<String> valuesInOrder = new ArrayList<>();
        valuesInOrder.add(newRow.get(ColumnName.STUDENT_ID));
        
    }
}
