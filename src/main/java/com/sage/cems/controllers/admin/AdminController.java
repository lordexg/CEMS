package com.sage.cems.controllers.admin;

import com.sage.cems.models.user.User;
import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane mainView;
    public Button backBtn;
    private User admin = null;
    public AdminController(){}
    public AdminController(User user){
        this.admin = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            mainView.setCenter(ViewFactory.getInstance().getView(View.ADMIN_HOME));
        });
        ViewFactory.getInstance().getCurrentViewProperty().addListener((_, _, newVal) -> {
            switchView(newVal);
        });

//        backBtn.setOnAction(_ -> onBack());
//
//        ViewFactory.getInstance().backStackSizeProperty().addListener((_, _, newVal) -> {
//            backBtn.setVisible(!newVal.equals(0));
//        });
    }
    private void passAdminToHomeController() {
        AdminHomeController homeController = (AdminHomeController) ViewFactory.getInstance().getController(View.ADMIN_HOME);
        homeController.setAdmin(admin);
    }

    private void switchView(View view) {
        switch (view) {
            case ADMIN_HOME -> passAdminToHomeController();
            case ADMIN_COURSES -> {
            }
            case STUDENT_RESULTS -> {
            }
            case PROFILE -> {
            }
        }
        mainView.setCenter(ViewFactory.getInstance().getView(view));

    }

//    private void onBack() {
//        View view = ViewFactory.getInstance().getBackStack().pop();
//        ViewFactory.getInstance().backStackSizeProperty().set(ViewFactory.getInstance().getBackStack().size());
//        ViewFactory.getInstance().getCurrentViewProperty().set(view);
//    }
}