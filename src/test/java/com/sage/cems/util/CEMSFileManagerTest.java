package com.sage.cems.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
class CEMSFileManagerTest {
    @Test
    void getRowTest() {
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            List<Map<ColumnName, String>> results = fileManager.getRows(TableName.STUDENT, "1");
            for (Map<ColumnName, String> record : results) {
                for (String i : record.values())
                    System.out.print(i + "\t");
                System.out.println();
            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    void insertRowTest() {
        File file = new File("C:\\Users\\ov\\Documents\\CEMS\\STUDENT.txt");
        Map<ColumnName, String> newRow = new TreeMap<>();
        newRow.put(ColumnName.ACCOUNT_ID, "1");
        newRow.put(ColumnName.ACCOUNT_PASSWORD, "password");
        newRow.put(ColumnName.ACCOUNT_ROLE, "admin");
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            fileManager.insertRow(TableName.ACCOUNT, newRow);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        assertNotEquals(file.length(), 84);
    }
}