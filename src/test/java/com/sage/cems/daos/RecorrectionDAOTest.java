package com.sage.cems.daos;

import com.sage.cems.models.Recorrection;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RecorrectionDAOTest {

    @Test
    void getAllRecorrections() {
    }

    @Test
    void getRecorrection() {
    }

    @Test
    void testGetAllRecorrections() {
    }

    @Test
    void addRecorrection() throws IOException {
        RecorrectionDAO recorrectionDAO = new RecorrectionDAO(new CEMSFileManager());
        Recorrection recorrection = new Recorrection();
        recorrection.setExamID("3");
        recorrection.setStudentID("2");
        recorrection.setStudentMessage("Please give me A mark :(((");
        recorrectionDAO.addRecorrection(recorrection);
    }

    @Test
    void updateRecorrection() {
    }

    @Test
    void deleteRecorrection() throws IOException {
        RecorrectionDAO recorrectionDAO = new RecorrectionDAO(new CEMSFileManager());
        Recorrection recorrection = new Recorrection();
        recorrection = recorrectionDAO.getRecorrection("1");
        recorrectionDAO.deleteRecorrection(recorrection);
    }
}