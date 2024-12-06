package com.sage.cems.util;

public class Validator {
    public static boolean validateName(String name) {
        return name.matches("^[a-zA-Z0-9]{1,15}$");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^\\S+@\\S+\\.\\S+$");
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^01[0125][0-9]{8}$");
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 8 && password.length() <= 16;
    }

    public static boolean validateUserName(String userName) {
        return userName.matches("^[a-zA-Z0-9]+$");
    }
}

