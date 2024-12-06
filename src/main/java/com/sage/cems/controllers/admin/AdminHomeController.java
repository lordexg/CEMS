package com.sage.cems.controllers.admin;

import com.sage.cems.models.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminHomeController {
    public User admin;
    @FXML
    public Label welcomeMessage;

    public void setAdmin(User admin){
        this.admin = admin;
        updateHome();
    }
    private void updateHome() {
        welcomeMessage.setText("Hello Admin #" + admin.getID());
    }
}
