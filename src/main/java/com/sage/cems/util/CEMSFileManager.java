package com.sage.cems.util;

import com.sage.cems.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CEMSFileManager implements FileManager{

    public CEMSFileManager() throws IOException {
        File dir = new File(Configuration.FILES_DIR);
        if (!dir.exists()) {
            throw new IOException("Cannot access the files base");
        }
    }

    @Override
    public List<Map<ColumnName, String>> getRows(TableName tableName, Object keyWord) {
        return List.of();
    }

    @Override
    public void updateRow(TableName tableName, Object keyWord, Map<ColumnName, String> newRow) {

    }

    @Override
    public void insertRow(TableName tableName, Map<ColumnName, String> newRow) {

    }

    @Override
    public void deleteRaw(TableName tableName, Object keyWord) {

    }
}
