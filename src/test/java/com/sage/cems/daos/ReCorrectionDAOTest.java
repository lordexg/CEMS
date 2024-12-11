package com.sage.cems.daos;

import com.sage.cems.models.ReCorrection;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ReCorrectionDAOTest {

    @Test
    void getAllReCorrections() {
    }

    @Test
    void getReCorrection() {
    }

    @Test
    void testGetAllReCorrections() {
    }

    @Test
    void addReCorrection() throws IOException {
        ReCorrectionDAO recorrectionDAO = new ReCorrectionDAO(new CEMSFileManager());
        ReCorrection recorrection = new ReCorrection();
        recorrection.setExamID("3");
        recorrection.setStudentID("2");
        recorrection.setStudentMessage("Please give me A mark :(((");
        recorrectionDAO.addReCorrection(recorrection);
    }

    @Test
    void updateReCorrection() {
    }

    @Test
    void deleteReCorrection() throws IOException {
        ReCorrectionDAO recorrectionDAO = new ReCorrectionDAO(new CEMSFileManager());
        ReCorrection recorrection = recorrectionDAO.getRecorrection("1");
        recorrectionDAO.deleteReCorrection(recorrection);
    }
}