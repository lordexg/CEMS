package com.sage.cems.services;

import com.sage.cems.daos.UserDAO;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class LoginService {
    private static final String DOCUMENTATION_URL = "https://lordexg.github.io/CEMS/";
    private final UserDAO userDAO;

    public LoginService() throws IOException {
        this.userDAO = new UserDAO(new CEMSFileManager());
    }

    public User login(String userName, String password) throws Exception {
        User user = userDAO.getUser(userName);
        if (!user.getPassword().equals(password)) {
            throw new Exception();
        }
        return user;
    }

    public void showDocumentationPage() throws Exception {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(DOCUMENTATION_URL));
        } else {
            System.err.println("Desktop is not supported on this platform.");
        }
    }
}

