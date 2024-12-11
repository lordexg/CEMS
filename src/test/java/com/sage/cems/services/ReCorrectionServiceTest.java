package com.sage.cems.services;

import com.sage.cems.models.ReCorrection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReCorrectionServiceTest {

    @Test
    void submitReCorrection() throws IOException {
        ReCorrectionService recorrectionService = new ReCorrectionService();
        ReCorrection recorrection = new ReCorrection();
        recorrection.setExamID("3");
        recorrection.setStudentID("2");
        recorrection.setStudentMessage("Please give me A mark :(((");
        recorrectionService.submitReCorrection(recorrection);
    }

    @Test
    void hasRequestsReCorrection() throws IOException {
        ReCorrectionService recorrectionService = new ReCorrectionService();
        assertFalse(recorrectionService.hasRequestReCorrection("8","3"));
        assertTrue(recorrectionService.hasRequestReCorrection("2","3"));
    }
}