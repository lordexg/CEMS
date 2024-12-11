package com.sage.cems.daos;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.EnumHelper;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserDAO {
    private final FileManager fileManager;

    public UserDAO(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public User getUser(String ID) throws IOException {
        List<Map<ColumnName, String>> users = fileManager.getRows(TableName.ACCOUNT, ID);

        if (users.isEmpty()) {
            return new User();
        }

        Map<ColumnName, String> target = users.getFirst();

        User user = new User();
        user.setID(target.get(ColumnName.ACCOUNT_ID));
        user.setPassword(target.get(ColumnName.ACCOUNT_PASSWORD));
        user.setRole(Role.valueOf(target.get(ColumnName.ACCOUNT_ROLE)));
        return user;
    }

    public void addUser(User user) throws IOException {
        fileManager.insertRow(TableName.ACCOUNT, createUserMapADD(user));
    }

    public void updateUser(User user) throws IOException {
        fileManager.updateRow(TableName.ACCOUNT, createUserMapDU(user));
    }

    public void deleteUser(User user) throws IOException {
        fileManager.deleteRow(TableName.ACCOUNT, createUserMapDU(user));
    }

    // this function fills a map(row) from a User object
    private Map<ColumnName, String> createUserMapADD(User newUser) throws IOException {
        Map<ColumnName, String> userMap = new TreeMap<>();
        // generate new ID
        userMap.put(ColumnName.ACCOUNT_ID, EnumHelper.getNewPK(TableName.ACCOUNT));

        populateUserMap(userMap, newUser);
        return userMap;
    }

    private Map<ColumnName, String> createUserMapDU(User newUser) throws IOException {
        Map<ColumnName, String> userMap = new TreeMap<>();
        userMap.put(ColumnName.ACCOUNT_ID, newUser.getID());
        populateUserMap(userMap, newUser);
        return userMap;
    }

    // user ID is not here, it's implemented in every createUserMap function
    private void populateUserMap(Map<ColumnName, String> userMap, User user) throws IOException {
        userMap.put(ColumnName.ACCOUNT_PASSWORD, user.getPassword());
        userMap.put(ColumnName.ACCOUNT_ROLE, user.getRole().toString());
    }
}
