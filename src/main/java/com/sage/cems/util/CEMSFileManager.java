package com.sage.cems.util;

import com.sage.cems.Configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CEMSFileManager implements FileManager {

    public CEMSFileManager() throws IOException {
        if (!areAllFilesExist())
            throw new IOException("Cannot access the files base");
    }

    @Override
    public List<Map<ColumnName, String>> getRows(TableName tableName, Object keyWord) throws IOException {
        String filePath = getFilePath(tableName);
        List<Map<ColumnName, String>> results = new ArrayList<>();
        List<String> lines = getFileLines(filePath);
        String[] headers = lines.getFirst().split("\t");

        for (int i = 1; i < lines.size(); ++i) {
            String[] cells = lines.get(i).split("\t");
            boolean found = false;
            for (String cell : cells) {
                if (cell.contains(keyWord.toString())) {
                    found = true;
                    break;
                }
            }
            if (found) {
                results.add(arrayToMap(headers, cells));
            }
        }
        return results;
    }

    @Override
    public List<Map<ColumnName, String>> getAllRows(TableName tableName) throws IOException{
        String filePath = getFilePath(tableName);
        List<Map<ColumnName, String>> results = new ArrayList<>();
        List<String> lines = getFileLines(filePath);
        String[] headers = lines.getFirst().split("\t");

        for (int i = 1; i < lines.size(); ++i) {
            String[] cells = lines.get(i).split("\t");
            results.add(arrayToMap(headers, cells));
        }
        return results;
    }

    @Override
    public void updateRow(TableName tableName, Map<ColumnName, String> row) throws IOException {
        String filePath = getFilePath(tableName);
        List<String> lines = getFileLines(filePath);
        String[] headers = lines.getFirst().split("\t");

        for (int i = 1; i < lines.size(); ++i) {
            String[] cells = lines.get(i).split("\t");
            if (row.get(ColumnName.valueOf(headers[0].trim())).equals(cells[0].trim())) {
                for (int j = 0; j < cells.length; ++j) {
                    cells[j] = row.get(ColumnName.valueOf(headers[j].trim()));
                }
                lines.set(i, String.join("\t", cells));
                break;
            }
        }
        overrideFile(lines, filePath);
    }

    @Override
    public void insertRow(TableName tableName, Map<ColumnName, String> newRow) throws IOException {
        StringBuilder rowToAppend = new StringBuilder();
        String filePath = getFilePath(tableName);

        for (String cell : newRow.values()) {
            rowToAppend.append(cell).append("\t");
        }
        rowToAppend.deleteCharAt(rowToAppend.length() - 1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(rowToAppend.toString());
            writer.newLine();
        }
    }

    @Override
    public void deleteRow(TableName tableName, Map<ColumnName, String> row) throws IOException {
        String filePath = getFilePath(tableName);
        List<String> lines = getFileLines(filePath);
        String[] headers = lines.getFirst().split("\t");

        for (int i = 1; i < lines.size(); ++i) {
            String[] cells = lines.get(i).split("\t");
            boolean isSameRow = true;
            for (int j = 0; j < cells.length; ++j) {
                if (!row.get(ColumnName.valueOf(headers[j].trim())).equals(cells[j].trim())) {
                    isSameRow = false;
                    break;
                }
            }
            if (isSameRow) {
                lines.remove(i);
                break;
            }
        }
        overrideFile(lines, filePath);
    }

    @Override
    public Map<ColumnName, String> getLastRow(TableName tableName) throws IOException {
        String filePath = getFilePath(tableName);
        List<String> lines = getFileLines(filePath);
        String[] headers = lines.getFirst().split("\t");
        String[] lastLine = lines.getLast().split("\t");
        return arrayToMap(headers, lastLine);
    }

    /* Helper Methods */
    // For checking if the main directory exists and all its files
    private boolean areAllFilesExist() {
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
    // convert cells and headers arrays into map
    private Map<ColumnName, String> arrayToMap(String[] headers, String[] cells) {
        Map<ColumnName, String> record = new TreeMap<>();
        for (int j = 0; j < cells.length; ++j) {
            record.put(ColumnName.valueOf(headers[j].trim()), cells[j].trim());
        }
        return record;
    }
    // Return the file path from table name
    private String getFilePath(TableName tableName) {
        return Configuration.FILES_DIR + File.separator + tableName.toString() + ".txt";
    }
    // Converting a file to list of lines
    private List<String> getFileLines(String filePath) throws IOException{
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return new ArrayList<>(Arrays.asList(content.split("\n")));
    }
    // Rewrite the content of the file with a list of lines
    private void overrideFile(List<String > lines, String filePath) throws IOException {
        String content = String.join("\n", lines);
        content += "\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}