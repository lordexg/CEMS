package com.sage.cems;

import com.sage.cems.models.Model;
import com.sage.cems.views.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory.getInstance().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}