package com.sage.cems.models.user;

public class User {
    private String ID;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;

    public User() {

    }

    public User(String password, String firstName, String lastName,
                String email, String phoneNumber, String ID, Role role) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
        this.role = role;
    }

// =============== Getters ===============
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

    public String getID() {
        return ID;
    }

    public Role getRole() {
        return role;
    }

// =============== Setters ===============
    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
