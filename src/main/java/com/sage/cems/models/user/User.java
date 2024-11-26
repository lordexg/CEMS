package com.sage.cems.models.user;

import com.sage.cems.util.FileManager;

public abstract class User {
    private FileManager fileManager;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int ID;
    private UserDAO userDataDAO;
    private Role role;
    public enum Role {
        STUDENT,
        LECTURER,
        ADMIN
    }

    public User(String userName,
                String password, String firstName, String lastName,
                String email, String phoneNumber, int ID, Role role) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

// =============== Setters ===============
    public boolean setUserName(String userName) {
        boolean valid = Validator.validateName(userName);
        if(valid){
            this.userName = userName; // to keep the instance up-to-date
            UserDAO.updateUser(this);
        }
        return valid;
    }

    public boolean setPassword(String password) {
        if(password.length() > 16 || password.length() < 8) return false;
        this.password = password; // to keep the instance up-to-date
        UserDAO.updateUser(this);
        return true;
    }

    public boolean setFirstName(String firstName) {
        boolean valid = Validator.validateEmail(firstName);
        if(valid) {
            this.firstName = firstName;
            UserDAO.updateUser(this);
        }
        return valid;
    }

    public boolean setLastName(String lastName) {
        boolean valid = Validator.validateEmail(lastName);
        if(valid) {
            this.lastName = lastName;
            UserDAO.updateUser(this);
        }
        return valid;
    }

    public boolean setEmail(String email) {
        boolean valid = Validator.validateEmail(email);
        if(valid) {
            this.email = email;
            UserDAO.updateUser(this);
        }

        return valid;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        boolean valid = Validator.validatePhoneNumber(phoneNumber);
        if(valid) {
            this.phoneNumber = phoneNumber;
            UserDAO.updateUser(this);
        }
        return valid;
    }

    public void setID() {
        // get last row
        // get its ID
        // set this.id = lastRowID++
    }


// =============== Login ===============


}
