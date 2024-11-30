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
            List<Map<ColumnName, String>> results = fileManager.getRows(TableName.ACCOUNT, "STUDENT");
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
    void getAllRowsTest() {
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            List<Map<ColumnName, String>> results = fileManager.getAllRows(TableName.EXAM);
            for (Map<ColumnName, String> record : results) {
                /*for (String i : record.values())
                    System.out.print(i + "\t");*/
                System.out.println(record.get(ColumnName.EXAM_LENGTH));
                System.out.println();
            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    void getLastRowTest() {
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            Map<ColumnName, String> result = fileManager.getLastRow(TableName.EXAM);
            /*for (String i : result.values())
                System.out.print(i + "\t");*/
            System.out.println(result.get(ColumnName.EXAM_LENGTH));

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    void insertRowTest() {
        Map<ColumnName, String> newRow = new TreeMap<>();
        newRow.put(ColumnName.ACCOUNT_ID, "8");
        newRow.put(ColumnName.ACCOUNT_PASSWORD, "i");
        newRow.put(ColumnName.ACCOUNT_ROLE, "STUDENT");
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            fileManager.insertRow(TableName.ACCOUNT, newRow);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    void updateRowTest() {
        Map<ColumnName, String> newRow = new TreeMap<>();
        newRow.put(ColumnName.ACCOUNT_ID, "3");
        newRow.put(ColumnName.ACCOUNT_PASSWORD, "newPassword3");
        newRow.put(ColumnName.ACCOUNT_ROLE, "student");
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            fileManager.updateRow(TableName.ACCOUNT, newRow);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    void deleteRowTest() {
        Map<ColumnName, String> newRow = new TreeMap<>();
        newRow.put(ColumnName.ACCOUNT_ID, "65");
        newRow.put(ColumnName.ACCOUNT_PASSWORD, "bb");
        newRow.put(ColumnName.ACCOUNT_ROLE, "student");
        FileManager fileManager;
        try {
            fileManager = new CEMSFileManager();
            fileManager.deleteRow(TableName.ACCOUNT, newRow);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

}