package com.sage.cems.views;

import com.sage.cems.controllers.admin.AdminController;
import com.sage.cems.controllers.lecturer.LecturerController;
import com.sage.cems.controllers.student.StudentController;
import com.sage.cems.models.Student;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewFactory {
    private final ObjectProperty<View> studentSelectedMenuBtn = new SimpleObjectProperty<>(View.STUDENT_HOME);
    private final Map<View, AnchorPane> cache = new HashMap<>();
    private final Map<View, Object> controllers = new HashMap<>();

    // Singleton Pattern
    private static ViewFactory viewFactory;
    private ViewFactory() {}
    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null)
            viewFactory = new ViewFactory();
        return viewFactory;
    }

    /*
    * Generic Methods for all Roles
    * */
    public AnchorPane getView(View view) {
        if (cache.containsKey(view)) {
            return cache.get(view);
        }
        AnchorPane anchorPane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + view.getFilePath()));
            anchorPane = fxmlLoader.load();
            controllers.put(view, fxmlLoader.getController());
            cache.put(view, anchorPane);
            return anchorPane;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return anchorPane;
    }

    public Object getController(View view) {
        return controllers.get(view);
    }
    /*
    * Login Methods
    * */
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        createStage(loader, 850, 550);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    /*
    * Student Methods
    * */
    public ObjectProperty<View> getStudentSelectedMenuBtn() {
        return studentSelectedMenuBtn;
    }

    public void showStudentWindow(Student student) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/student.fxml"));
        StudentController studentController = new StudentController(student);
        loader.setController(studentController);
        createStage(loader, 1200, 720);
    }


    /*
    * Lecturer Methods
    * */
    public void showLecturerWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lecturer/lecturer.fxml"));
        LecturerController lecturerController = new LecturerController();
        loader.setController(lecturerController);
        createStage(loader, 1200, 720);
    }

    /*
    * Admin Methods
    * */
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader, 1200, 720);
    }

    /*
     * Helper Methods
     * */
    private void createStage(FXMLLoader loader, double width, double height) {
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
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setMinWidth(width*0.85);
        stage.setMinHeight(height*0.85);
        stage.show();
    }

}
