package com.sage.cems.daos;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private final FileManager fileManager;
    public UserDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void updateUser(User user) {
        Map<ColumnName, String> updatedUser = fillUserMap(user);
        fileManager.updateRow(TableName.ACCOUNT, updatedUser);
    }

    public User getUser(long ID) throws IOException {
        List<Map<ColumnName, String>> users = fileManager.getRows(TableName.ACCOUNT, ID);
        Map<ColumnName, String> target = users.getFirst();
        User user = new User();
        user.setID(target.get(ColumnName.ACCOUNT_ID));
        user.setPassword(target.get(ColumnName.ACCOUNT_PASSWORD));
        user.setRole(Role.valueOf(target.get(ColumnName.ACCOUNT_ROLE)));
        return user;
    }

    public void addUser(User user) {
        Map<ColumnName, String> newUser = fillUserMap(user);
        fileManager.insertRow(TableName.ACCOUNT, newUser);
    }

    public void deleteUser(User user) {
        fileManager.deleteRaw(TableName.ACCOUNT, user.getID());
    }

    // this function fills a map(row) from a User object
    private Map<ColumnName, String> fillUserMap(User newUser){
        Map<ColumnName, String> user = new HashMap<>();
        user.put(ColumnName.ACCOUNT_ID, newUser.getID());
        user.put(ColumnName.ACCOUNT_PASSWORD, newUser.getPassword());
        user.put(ColumnName.ACCOUNT_ROLE, newUser.getRole().toString());
        return user;
    }
}


