package com.sage.cems.daos;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.ColumnName;
import com.sage.cems.util.FileManager;
import com.sage.cems.util.TableName;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static final FileManager fileManager;

    // cannot throw checked exceptions
    static{
        try {
            fileManager = new CEMSFileManager();
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize file manager", e);
        }
    }

    public static void main(String[] args) throws IOException {
        User x = UserDAO.getUser(1);
    }

    public static void updateUser(User user) {
        Map<ColumnName, String> updatedUser = fillMapFromUser(user);
        fileManager.updateRow(TableName.ACCOUNT, updatedUser);
    }

    public static User getUser(long ID) throws IOException {
        try{
            List<Map<ColumnName, String>> users = fileManager.getRows(TableName.ACCOUNT, ID);
            Map<ColumnName, String> target = users.getFirst();

            User user = new User();
            user.setID(target.get(ColumnName.ACCOUNT_ID));
            user.setPassword(target.get(ColumnName.ACCOUNT_PASSWORD));
            user.setRole(Role.valueOf(target.get(ColumnName.ACCOUNT_ROLE)));
            return user;
        }catch(IOException e){
            throw new IOException("Cannot get user for ID: " + ID, e);
        }
    }

    public static void addUser(User user) throws IOException {
        Map<ColumnName, String> newUser = fillMapFromUser(user);
        fileManager.insertRow(TableName.ACCOUNT, newUser);
    }

    public static void deleteUser(User user) {
        fileManager.deleteRaw(TableName.ACCOUNT, user.getID());
    }

    // this function fills a map(row) from a User object
    private static Map<ColumnName, String> fillMapFromUser(User newUser){
        Map<ColumnName, String> user = new HashMap<>();
        user.put(ColumnName.ACCOUNT_ID, newUser.getID());
        user.put(ColumnName.ACCOUNT_PASSWORD, newUser.getPassword());
        user.put(ColumnName.ACCOUNT_ROLE, newUser.getRole().toString());
        return user;
    }

}


