package com.sage.cems.models;

import com.sage.cems.models.utils.FileManager;
import com.sage.cems.models.utils.TableType;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int ID;
    private Role role;
    private final FileManager fileManager;
    public enum Role {
        STUDENT,
        LECTURER,
        ADMIN
    }

    public User(FileManager fileManager, String userName, String password, String firstName, String lastName, String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fileManager = fileManager;
    }

// =============== neither getters nor setters ===============
    public void updateDB(){
        //  List<Map<String, String>> row = fileManager.getRow(TableType.USER, this.ID);
        // pre-condition: attributes up-to-date
        // get row by id
            // if exist -> update everything & push
            // else -> insert
    }

    // this method for first/last name validation;
    public boolean validateName(String name){
        return name.matches("^[a-zA-Z0-9]{1,15}$");
    }

// =============== Getters ===============
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getID() {
        return ID;
    }

// =============== Setters ===============
    public boolean setUserName(String userName) {
        boolean valid = userName.matches("^[a-zA-Z0-9]+$");
        if(valid){
            this.userName = userName; // to keep the instance up-to-date
            updateDB();
        }
        return valid;
    }

    public boolean setPassword(String password) {
        if(password.length() > 16 || password.length() < 8) return false;
        this.password = password; // to keep the instance up-to-date
        updateDB();
        return true;
    }

    public boolean setFirstName(String firstName) {
        boolean valid = validateName(firstName);
        if(valid) {
            this.firstName = firstName;
            updateDB();
        }
        return valid;
    }

    public boolean setLastName(String lastName) {
        boolean valid = validateName(lastName);
        if(valid) {
            this.lastName = lastName;
            updateDB();
        }
        return valid;
    }

    public boolean setEmail(String email) {
        boolean valid = email.matches("^\\S+@\\S+\\.\\S+$");
        if(valid) {
            this.email = email;
            updateDB();
        }

        return valid;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        boolean valid = phoneNumber.matches("^01[0125][0-9]{8}$");
        if(valid) {
            this.phoneNumber = phoneNumber;
            updateDB();
        }
        return valid;
    }

    public void setID() {
        // get last row
        // get its ID
        // set this.id = lastRowID++
    }


// =============== Login ===============
    public boolean login(String userName, String password) {
        // login logic

        return this.userName.equals(userName) && this.password.equals(password);
    }

    public void logout() {
        // logout logic
    }

}
