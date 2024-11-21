package com.sage.cems.models.utils;

import java.util.List;
import java.util.Map;

public class CEMSFileManager implements FileManager{

    @Override
    public List<Map<String, String>> getRow(TableType tableType, Object keyWord) {
        return List.of();
    }

    @Override
    public void updateRow(TableType tableType, Object keyWord, Map<String, String> newRow) {

    }

    @Override
    public void insertNewRow(TableType tableType, Map<String, String> newRow) {

    }

    @Override
    public void deleteRaw(TableType tableType, Object keyWord) {

    }
}
