package com.sage.cems.daos;

import com.sage.cems.models.ReCorrection;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.EnumHelper;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.*;

public class ReCorrectionDAO {

    private final FileManager fileManager;

    public ReCorrectionDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * @param examID to retrieve the reCorrections for the specified examID
     * */
    public List<ReCorrection> getAllReCorrections(String examID) throws IOException {
        List<Map<ColumnName, String>> allMatchedReCorrections = fileManager.getRows(TableName.RECORRECTION, examID);

        if (allMatchedReCorrections.isEmpty()) {
            return new ArrayList<>();
        }

        return createRecorrectionsList(allMatchedReCorrections);
    }

    public ReCorrection getRecorrection(String requestID) throws IOException {
        List<Map<ColumnName, String>> matchedReCorrectionMap = fileManager.getRows(TableName.RECORRECTION, requestID);
        Map<ColumnName, String> recorrectionMap = new TreeMap<>();

        // reCorrectionID might conflict with any other column (filtration needed)
        for (Map<ColumnName, String> matchedReCorrection : matchedReCorrectionMap) {
            if(Objects.equals(matchedReCorrection.get(ColumnName.RECORRECTION_ID), requestID)) {
                recorrectionMap = matchedReCorrection;
                break;
            }
        }

        return createRecorrection(recorrectionMap);
    }

    public List<ReCorrection> getAllReCorrections() throws IOException {
        List<Map<ColumnName, String>> reCorrections = fileManager.getAllRows(TableName.RECORRECTION);
        if(reCorrections.isEmpty()) {
            return new ArrayList<>();
        }
        return createRecorrectionsList(reCorrections);
    }

    public void addReCorrection(ReCorrection recorrection) throws IOException {
        fileManager.insertRow(TableName.RECORRECTION, createReCorrectionMapADD(recorrection));
    }

    public void updateReCorrection(ReCorrection recorrection) throws IOException {
        fileManager.updateRow(TableName.RECORRECTION, createReCorrectionMapDU(recorrection));
    }

    public void deleteReCorrection(ReCorrection recorrection) throws IOException {
        fileManager.deleteRow(TableName.RECORRECTION, createReCorrectionMapDU(recorrection));
    }

    private void populateReCorrectionFields(ReCorrection recorrection, Map<ColumnName, String> recorrectionMap){
        recorrection.setExamID(recorrectionMap.get(ColumnName.EXAM_ID));
        recorrection.setStudentID(recorrectionMap.get(ColumnName.STUDENT_ID));
        recorrection.setReCorrectionID(recorrectionMap.get(ColumnName.RECORRECTION_ID));
        recorrection.setStudentMessage(recorrectionMap.get(ColumnName.STUDENT_MESSAGE));
        recorrection.setRequestStatus(Boolean.parseBoolean(recorrectionMap.get(ColumnName.REQUEST_STATUS)));
    }

    private ReCorrection createRecorrection(Map<ColumnName, String> recorrectionMap){
        ReCorrection recorrection = new ReCorrection();
        populateReCorrectionFields(recorrection, recorrectionMap);
        return recorrection;
    }

    // Used in some functions that interacts with the CEMS File System (insertion, deletion, update)
    // used to add reCorrection
    private Map<ColumnName, String> createReCorrectionMapADD(ReCorrection recorrection) throws IOException {
        Map<ColumnName, String> newReCorrection = new TreeMap<>();
        populateReCorrectionMap(newReCorrection, recorrection);
        newReCorrection.put(ColumnName.RECORRECTION_ID, EnumHelper.getNewPK(TableName.RECORRECTION));
        return newReCorrection;
    }
    // used to delete/update an reCorrection
    private Map<ColumnName, String> createReCorrectionMapDU(ReCorrection recorrection){
        Map<ColumnName, String> newReCorrection = new TreeMap<>();
        populateReCorrectionMap(newReCorrection, recorrection);
        newReCorrection.put(ColumnName.RECORRECTION_ID, recorrection.getReCorrectionID());
        return newReCorrection;
    }

    /**
     *  populates an ReCorrectionMap without adding an reCorrectionID, cus different createReCorrectionMap functions will implement it
     */
    private void populateReCorrectionMap(Map<ColumnName, String> newReCorrection, ReCorrection recorrection){
        newReCorrection.put(ColumnName.RECORRECTION_ID,recorrection.getReCorrectionID());
        newReCorrection.put(ColumnName.STUDENT_ID, recorrection.getStudentID());
        newReCorrection.put(ColumnName.EXAM_ID,recorrection.getExamID());
        newReCorrection.put(ColumnName.REQUEST_STATUS, String.valueOf(recorrection.isRequestStatus()));
        newReCorrection.put(ColumnName.STUDENT_MESSAGE,recorrection.getStudentMessage());
    }

    // create reCorrections list
    private List<ReCorrection> createRecorrectionsList(List<Map<ColumnName, String>> reCorrections){
        List<ReCorrection> recorrectionsList = new ArrayList<>();
        for (Map<ColumnName, String> reCorrection : reCorrections) {
            recorrectionsList.add(createRecorrection(reCorrection));
        }
        return recorrectionsList;
    }
    
}
