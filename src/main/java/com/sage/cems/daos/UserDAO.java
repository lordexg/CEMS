package com.sage.cems.daos;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;
import com.sage.cems.util.ColumnName;
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

    public void updateUser(User user) throws IOException {
        fileManager.updateRow(TableName.ACCOUNT, createUserMap(user));
    }

    public User getUser(String ID) throws IOException {
            List<Map<ColumnName, String>> users = fileManager.getRows(TableName.ACCOUNT, ID);

            if (users.isEmpty()) {
                throw new IOException("No user found");
            }

            Map<ColumnName, String> target = users.getFirst();

            User user = new User();
            user.setID(target.get(ColumnName.ACCOUNT_ID));
            user.setPassword(target.get(ColumnName.ACCOUNT_PASSWORD));
            user.setRole(Role.valueOf(target.get(ColumnName.ACCOUNT_ROLE)));
            return user;
    }

    public void addUser(User user) throws IOException {
        fileManager.insertRow(TableName.ACCOUNT, createUserMap(user));
    }

    public void deleteUser(User user) throws IOException {
        fileManager.deleteRow(TableName.ACCOUNT, createUserMap(user));
    }

    // this function fills a map(row) from a User object
    private Map<ColumnName, String> createUserMap(User newUser) throws IOException {
        Map<ColumnName, String> user = new TreeMap<>();

        // calculating the ID based on the last row in ACCOUNT TABLE
        String lastIDString = fileManager.getLastRow(TableName.ACCOUNT).get(ColumnName.ACCOUNT_ID);
        int newIDInt;

        // to handle empty Table --- lastIDString = "String without a Number in it"
        try{
            newIDInt = Integer.parseInt(lastIDString) + 1;
        }catch(NumberFormatException e){
            newIDInt = 1;
        }
        String newIDString = Integer.toString(newIDInt);

        user.put(ColumnName.ACCOUNT_ID, newIDString);
        user.put(ColumnName.ACCOUNT_PASSWORD, newUser.getPassword());
        user.put(ColumnName.ACCOUNT_ROLE, newUser.getRole().toString());
        return user;
    }
}


