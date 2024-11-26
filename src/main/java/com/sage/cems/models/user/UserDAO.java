package com.sage.cems.models.user;

import com.sage.cems.util.FileManager;

public class UserDAO {
    private FileManager fileManager;

    public UserDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public static void updateUser(User user) {
        // Implement database update logic
        // List<Map<String, String>> row = fileManager.getRow(TableType.USER, user.getID());
        // if exist -> update everything & push
        // else -> insert
    }
}


