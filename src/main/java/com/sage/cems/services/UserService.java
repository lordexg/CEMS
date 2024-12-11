package com.sage.cems.services;

import com.sage.cems.daos.LecturerDAO;
import com.sage.cems.daos.StudentDAO;
import com.sage.cems.daos.UserDAO;
import com.sage.cems.models.user.User;
import com.sage.cems.util.CEMSFileManager;
import com.sage.cems.util.FileManager;

import java.io.IOException;

public class UserService {
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;
    private final LecturerDAO lecturerDAO;
//    private final AdminDAO adminDAO;

    public UserService() throws IOException {
        FileManager fileManager = new CEMSFileManager();
        this.userDAO = new UserDAO(fileManager);
        this.studentDAO = new StudentDAO(fileManager);
        this.lecturerDAO = new LecturerDAO(fileManager);
//        this.adminDAO = new AdminDAO(fileManager);
    }

    public void updateUser(User user) throws IOException {
        userDAO.updateUser(user);
        switch (user.getRole()) {
            case STUDENT -> studentDAO.updateStudent(user);
            case LECTURER -> {
                return;
            }
//            case ADMIN -> ;
        }
    }

}
