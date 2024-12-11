package com.sage.cems.daos;

import com.sage.cems.models.Recorrection;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.EnumHelper;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.*;

public class RecorrectionDAO {

    private final FileManager fileManager;

    public RecorrectionDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * @param examID to retrieve the recorrections for the specified examID
     * */
    public List<Recorrection> getAllRecorrections(String examID) throws IOException {
        List<Map<ColumnName, String>> allMatchedRecorrections = fileManager.getRows(TableName.RECORRECTION, examID);

        if (allMatchedRecorrections.isEmpty()) {
            return new ArrayList<>();
        }

        return createRecorrectionsList(allMatchedRecorrections);
    }

    public Recorrection getRecorrection(String requestID) throws IOException {
        List<Map<ColumnName, String>> matchedRecorrectionMap = fileManager.getRows(TableName.RECORRECTION, requestID);
        Map<ColumnName, String> recorrectionMap = new TreeMap<>();

        // recorrectionID might conflict with any other column (filtration needed)
        for (Map<ColumnName, String> matchedRecorrection : matchedRecorrectionMap) {
            if(Objects.equals(matchedRecorrection.get(ColumnName.RECORRECTION_ID), requestID)) {
                recorrectionMap = matchedRecorrection;
                break;
            }
        }

        return createRecorrection(recorrectionMap);
    }

    public List<Recorrection> getAllRecorrections() throws IOException {
        List<Map<ColumnName, String>> recorrections = fileManager.getAllRows(TableName.RECORRECTION);
        if(recorrections.isEmpty()) {
            return new ArrayList<>();
        }
        return createRecorrectionsList(recorrections);
    }

    public void addRecorrection(Recorrection recorrection) throws IOException {
        fileManager.insertRow(TableName.RECORRECTION, createRecorrectionMapADD(recorrection));
    }

    public void updateRecorrection(Recorrection recorrection) throws IOException {
        fileManager.updateRow(TableName.RECORRECTION, createRecorrectionMapDU(recorrection));
    }

    public void deleteRecorrection(Recorrection recorrection) throws IOException {
        fileManager.deleteRow(TableName.RECORRECTION, createRecorrectionMapDU(recorrection));
    }

    private void populateRecorrectionFields(Recorrection recorrection, Map<ColumnName, String> recorrectionMap){
        recorrection.setExamID(recorrectionMap.get(ColumnName.EXAM_ID));
        recorrection.setStudentID(recorrectionMap.get(ColumnName.STUDENT_ID));
        recorrection.setRECORRECTION_ID(recorrectionMap.get(ColumnName.RECORRECTION_ID));
        recorrection.setStudentMessage(recorrectionMap.get(ColumnName.STUDENT_MESSAGE));
        recorrection.setRequestStatus(Boolean.parseBoolean(recorrectionMap.get(ColumnName.REQUEST_STATUS)));
    }

    private Recorrection createRecorrection(Map<ColumnName, String> recorrectionMap){
        Recorrection recorrection = new Recorrection();
        populateRecorrectionFields(recorrection, recorrectionMap);
        return recorrection;
    }

    // Used in some functions that interacts with the CEMS File System (insertion, deletion, update)
    // used to add recorrection
    private Map<ColumnName, String> createRecorrectionMapADD(Recorrection recorrection) throws IOException {
        Map<ColumnName, String> newRecorrection = new TreeMap<>();
        populateRecorrectionMap(newRecorrection, recorrection);
        newRecorrection.put(ColumnName.RECORRECTION_ID, EnumHelper.getNewPK(TableName.RECORRECTION));
        return newRecorrection;
    }
    // used to delete/update an recorrection
    private Map<ColumnName, String> createRecorrectionMapDU(Recorrection recorrection){
        Map<ColumnName, String> newRecorrection = new TreeMap<>();
        populateRecorrectionMap(newRecorrection, recorrection);
        newRecorrection.put(ColumnName.RECORRECTION_ID, recorrection.getRECORRECTION_ID());
        return newRecorrection;
    }

    /**
     *  populates an RecorrectionMap without adding an recorrectionID, cus different createRecorrectionMap functions will implement it
     */
    private void populateRecorrectionMap(Map<ColumnName, String> newRecorrection, Recorrection recorrection){
        newRecorrection.put(ColumnName.RECORRECTION_ID,recorrection.getRECORRECTION_ID());
        newRecorrection.put(ColumnName.STUDENT_ID, recorrection.getStudentID());
        newRecorrection.put(ColumnName.EXAM_ID,recorrection.getExamID());
        newRecorrection.put(ColumnName.REQUEST_STATUS, String.valueOf(recorrection.isRequestStatus()));
        newRecorrection.put(ColumnName.STUDENT_MESSAGE,recorrection.getStudentMessage());
    }

    // create recorrections list
    private List<Recorrection> createRecorrectionsList(List<Map<ColumnName, String>> recorrections){
        List<Recorrection> recorrectionsList = new ArrayList<>();
        for (Map<ColumnName, String> recorrection : recorrections) {
            recorrectionsList.add(createRecorrection(recorrection));
        }
        return recorrectionsList;
    }
    
}
