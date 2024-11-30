package com.sage.cems.daos;

import com.sage.cems.models.Lecturer;
import com.sage.cems.models.user.Role;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LecturerDAO {
    private final FileManager fileManager;


    public LecturerDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void addLecturer(Lecturer lecturer) throws IOException {
        fileManager.insertRow(TableName.LECTURER, createLecturerMap(lecturer));
    }

    public void deleteLecturer(Lecturer lecturer) throws IOException {
        fileManager.deleteRow(TableName.LECTURER, createLecturerMap(lecturer));
    }

    public List<Lecturer> getAllLecturer(String keyWord) throws IOException {
        List<Map<ColumnName, String>> lecturers = fileManager.getRows(TableName.LECTURER, keyWord);

        if (lecturers.isEmpty()) {
            throw new IOException("No student found");
        }

        return createLecturerList(lecturers);
    }

    public List<Lecturer> getAllLecturer() throws IOException {
        List<Map<ColumnName, String>> lecturers = fileManager.getAllRows(TableName.LECTURER);

        if (lecturers.isEmpty()) {
            throw new IOException("No student found");
        }

        return createLecturerList(lecturers);
    }

    public void updateLecturer(Lecturer lecturer) throws IOException {
        fileManager.updateRow(TableName.LECTURER, createLecturerMap(lecturer));
    }

    private void populateLecturerFields(Lecturer lecturer, Map<ColumnName, String> lecturerMap, Map<ColumnName, String> lecturerAccountMap) {
        lecturer.setID(lecturerMap.get(ColumnName.LECTURER_ID));
        lecturer.setFirstName(lecturerMap.get(ColumnName.LECTURER_FIRST_NAME));
        lecturer.setLastName(lecturerMap.get(ColumnName.LECTURER_LAST_NAME));
        lecturer.setEmail(lecturerMap.get(ColumnName.LECTURER_EMAIL));
        lecturer.setPhoneNumber(lecturerMap.get(ColumnName.LECTURER_PHONE_NUMBER));
        lecturer.setPassword(lecturerAccountMap.get(ColumnName.ACCOUNT_PASSWORD));
        lecturer.setRole(Role.valueOf(lecturerAccountMap.get(ColumnName.ACCOUNT_ROLE)));
    }

    private Lecturer createLecturer(Map<ColumnName, String> lecturerMap, Map<ColumnName, String> lecturerAccountMap) {
        Lecturer lecturer = new Lecturer();
        populateLecturerFields(lecturer, lecturerMap, lecturerAccountMap);
        return lecturer;
    }

    private Map<ColumnName, String> createLecturerMap(Lecturer lecturer){
        Map<ColumnName, String> newLecturer = new TreeMap<>();
        newLecturer.put(ColumnName.LECTURER_ID, lecturer.getID());
        newLecturer.put(ColumnName.LECTURER_FIRST_NAME, lecturer.getFirstName());
        newLecturer.put(ColumnName.LECTURER_LAST_NAME, lecturer.getLastName());
        newLecturer.put(ColumnName.LECTURER_PHONE_NUMBER, lecturer.getPhoneNumber());
        newLecturer.put(ColumnName.LECTURER_EMAIL, lecturer.getEmail());
        return newLecturer;
    }

    private List<Lecturer> createLecturerList(List<Map<ColumnName, String>> lecturers) throws IOException {

        List<Map<ColumnName, String>> accounts = new ArrayList<>();
        for (Map<ColumnName, String> lecturer : lecturers) {
            String userID = lecturer.get(ColumnName.LECTURER_ID);
            Map<ColumnName, String> account = fileManager.getRows(TableName.ACCOUNT, userID).getFirst();
            accounts.add(account);
        }

        List<Lecturer> LecturerList = new ArrayList<>();
        for(int i = 0; i < lecturers.size(); i++) {
            LecturerList.add(createLecturer(lecturers.get(i), accounts.get(i)));
        }
        return LecturerList;
    }
}
