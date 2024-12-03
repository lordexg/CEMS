package com.sage.cems.daos;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void updateUser() throws IOException {
        UserDAO userDAO = new UserDAO(new CEMSFileManager());
        User user = new User();
        user.setPassword("awwwa");
        user.setRole(Role.valueOf("ADMIN"));
        user.setID("2");
        userDAO.updateUser(user);
    }

    @Test
    void getUser() {
    }

    @Test
    void addUser() throws IOException {
        UserDAO userDAO = new UserDAO(new CEMSFileManager());
        User user = new User();
        user.setPassword("a");
        user.setRole(Role.valueOf("STUDENT"));
        userDAO.addUser(user);
    }

    @Test
    void deleteUser() throws IOException {
        UserDAO userDAO = new UserDAO(new CEMSFileManager());
        User user = new User();
        user.setPassword("aa");
        user.setRole(Role.valueOf("ADMIN"));
        user.setID("2");
        userDAO.deleteUser(user);
    }
}