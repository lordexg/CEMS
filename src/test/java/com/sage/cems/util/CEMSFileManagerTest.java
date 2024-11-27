package com.sage.cems.util;

import org.junit.jupiter.api.Test;

import static com.sage.cems.util.CEMSFileManager.isAllFilesExist;
import static org.junit.jupiter.api.Assertions.*;

class CEMSFileManagerTest {
    @Test
    void isAllFilesExistTest() {
        assertTrue(isAllFilesExist());
    }


}