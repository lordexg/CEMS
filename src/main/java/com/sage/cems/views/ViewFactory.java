package com.sage.cems.views;

import com.sage.cems.controllers.PopUpController;
import com.sage.cems.controllers.admin.AdminController;
import com.sage.cems.controllers.lecturer.LecturerController;
import com.sage.cems.controllers.student.ExamStageController;
import com.sage.cems.controllers.student.StudentController;
import com.sage.cems.models.Exam;
import com.sage.cems.models.Student;
import com.sage.cems.models.user.User;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewFactory {
    private ObjectProperty<View> currentView = new SimpleObjectProperty<>();
    private final Map<View, Pair<AnchorPane, Object>> cache = new HashMap<>();
    private final Stack<View> backStack = new Stack<>();
    private final IntegerProperty backStackSize = new SimpleIntegerProperty(0);

    // Singleton Pattern
    private static ViewFactory viewFactory;
    private ViewFactory() {}
    public static synchronized ViewFactory getInstance() {
        if (viewFactory == null)
            viewFactory = new ViewFactory();
        return viewFactory;
    }

    /*
    * Generic Methods for all Roles and Views
    * */
    public AnchorPane getView(View view) {
        if (cache.containsKey(view)) {
            return cache.get(view).getKey();
        }
        AnchorPane anchorPane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + view.getFilePath()));
            anchorPane = fxmlLoader.load();
            cache.put(view, new Pair<>(anchorPane, fxmlLoader.getController()));
            return anchorPane;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return anchorPane;
    }

    public Object getController(View view) {
        return cache.get(view).getValue();
    }

    public ObjectProperty<View> getCurrentViewProperty() {
        return currentView;
    }

    public void closeStage(Stage stage) {
        stage.close();
        currentView = new SimpleObjectProperty<>();
        cache.clear();
    }

    public Stack<View> getBackStack() {
        return backStack;
    }

    public IntegerProperty backStackSizeProperty() {
        return backStackSize;
    }

    public boolean showPopUp(String header, String message, PopUpType popUpType) {
        AtomicBoolean popUpResult = new AtomicBoolean(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pop-up.fxml"));
        PopUpController controller = new PopUpController(header, message, popUpType, popUpResult);
        loader.setController(controller);
        Image icon = switch (popUpType) {
            case INFO, CONFIRMATION -> new Image(Objects.requireNonNull(getClass().getResource("/images/Info.png")).toExternalForm());
            case WARNING -> new Image(Objects.requireNonNull(getClass().getResource("/images/Warning.png")).toExternalForm());
        };
        Stage popUpStage = creatBasicStage(loader, popUpType.toString(), icon, 450, 320);
        popUpStage.initModality(Modality.APPLICATION_MODAL); // Block main window interaction
        popUpStage.setResizable(false);
        popUpStage.showAndWait();
        return popUpResult.get();
    }

    /*
    * Login Methods
    * */
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        createStage(loader, 850, 550);
    }

    /*
    * Student Methods
    * */
    public void showStudentWindow(Student student) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/student.fxml"));
        StudentController studentController = new StudentController(student);
        loader.setController(studentController);
        createStage(loader, 1200, 720);
    }

    public void showExamWindow(Exam exam, String studentId, Stage parent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/exam-stage.fxml"));
        ExamStageController controller = new ExamStageController(exam, studentId, parent);
        loader.setController(controller);
        createStage(loader, 900, 950);
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
    public void showAdminWindow(User user) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/admin.fxml"));
        AdminController adminController = new AdminController(user);
        loader.setController(adminController);
        createStage(loader, 1200, 720);
    }

    /*
     * Helper Methods
     * */
    private void createStage(FXMLLoader loader, double width, double height) {
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/images/exam.png")).toExternalForm());
        Stage stage = creatBasicStage(loader, "College Examination Management System", icon, width, height);
        stage.setMinWidth(width*0.85);
        stage.setMinHeight(height*0.85);
        stage.show();
    }

    private Stage creatBasicStage(FXMLLoader loader, String title, Image icon, double width, double height) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        return stage;
    }

}
