package com.sage.cems.daos;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void updateUser() {
    }

    @Test
    void getUser() {
    }

    @Test
    void addUser() throws IOException {
        UserDAO userDAO = new UserDAO(new CEMSFileManager());
        User user = new User();
        user.setPassword("123");
        user.setEmail("ahmed@aosf");
        user.setRole(Role.valueOf("STUDENT"));
        userDAO.addUser(user);
    }

    @Test
    void deleteUser() {
    }
}