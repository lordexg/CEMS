package com.sage.cems.services;

import com.sage.cems.daos.ReCorrectionDAO;
import com.sage.cems.models.ReCorrection;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;

import java.io.IOException;
import java.util.List;

public class ReCorrectionService {

    private final ReCorrectionDAO recorrectionDAO;

    public ReCorrectionService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.recorrectionDAO = new ReCorrectionDAO(fileManager);
    }

    public void submitReCorrection(ReCorrection recorrection) throws IOException {
        recorrectionDAO.addReCorrection(recorrection);
    }

    public boolean hasRequestReCorrection(String studentID, String examID) throws IOException {
        List<ReCorrection> reCorrections = recorrectionDAO.getAllReCorrections(examID);
        for(ReCorrection recorrection : reCorrections){
            if(recorrection.getStudentID().equals(studentID)){
                return true;
            }
        }
        return false;
    }
}
