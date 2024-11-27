package com.sage.cems.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewFactory {



    // Login Methods
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Stage stage = createStage(loader);
        stage.setMinWidth(850);
        stage.setMinHeight(550);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }


    // Helper Methods
    private Stage createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        Stage stage = new Stage();
        stage.setTitle("College Examination Management System");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/exam.png")).toExternalForm()));
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}
