package com.sage.cems.services;

import com.sage.cems.models.Recorrection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RecorrectionServiceTest {

    @Test
    void submitReCorrection() throws IOException {
        RecorrectionService recorrectionService = new RecorrectionService();
        Recorrection recorrection = new Recorrection();
        recorrection.setExamID("3");
        recorrection.setStudentID("2");
        recorrection.setStudentMessage("Please give me A mark :(((");
        recorrectionService.submitReCorrection(recorrection);
    }

    @Test
    void hasRequestsReCorrection() throws IOException {
        RecorrectionService recorrectionService = new RecorrectionService();
        assertFalse(recorrectionService.hasRequestsReCorrection("8","3"));
        assertTrue(recorrectionService.hasRequestsReCorrection("2","3"));
    }
}