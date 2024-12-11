package com.sage.cems.services;

import com.sage.cems.daos.RecorrectionDAO;
import com.sage.cems.models.Recorrection;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;

import java.io.IOException;
import java.util.List;

public class RecorrectionService {

    private final RecorrectionDAO recorrectionDAO;

    public RecorrectionService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.recorrectionDAO = new RecorrectionDAO(fileManager);
    }

    public void submitReCorrection(Recorrection recorrection) throws IOException {
        recorrectionDAO.addRecorrection(recorrection);
    }

    public boolean hasRequestsReCorrection(String studentID, String examID) throws IOException {
        List<Recorrection> recorrections = recorrectionDAO.getAllRecorrections(examID);
        for(Recorrection recorrection : recorrections){
            if(recorrection.getStudentID().equals(studentID)){
                return true;
            }
        }
        return false;
    }
}
