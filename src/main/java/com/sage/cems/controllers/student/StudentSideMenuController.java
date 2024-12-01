package com.sage.cems.controllers.student;

import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentSideMenuController implements Initializable {

    public AnchorPane parent;
    public Button menuButton;
    public Button homeBtn;
    public Button coursesBtn;
    public Button resultsBtn;
    public Button profileBtn;
    public Button logoutBtn;
    public VBox menuButtonsVBox;

    private boolean fullSideMenu = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuButton.setOnAction(actionEvent -> toggleSideMenu());
        handleButtons();
    }

    private void toggleSideMenu() {
        Timeline timeline = new Timeline();
        KeyValue widthValue = new KeyValue(parent.prefWidthProperty(), fullSideMenu ? 60.0 : 200.0);
        fullSideMenu = !fullSideMenu;
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), widthValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void handleButtons() {
        for (Node node : menuButtonsVBox.getChildren()) {
            if (node.equals(menuButton))
                continue;
            updateView(node);
        }
        updateView(profileBtn);
        logoutBtn.setOnAction(actionEvent -> onLogout());

    }

    private void onLogout(){
        ViewFactory.getInstance().closeStage((Stage) homeBtn.getScene().getWindow());
        ViewFactory.getInstance().showLoginWindow();
    }

    private void updateView(Node node) {
        Button button = (Button) node;
        button.setOnAction(actionEvent -> ViewFactory.getInstance().getStudentCurrentView().set(View.valueOf(button.getId())));
        ViewFactory.getInstance().getStudentCurrentView().addListener((obs, oldVal, newVal) -> {
            ImageView imageView = (ImageView) button.getGraphic();
            if (newVal.equals(View.valueOf(button.getId()))) {
                button.getStyleClass().add("active-btn");
                String imageUrl = imageView.getImage().getUrl().replace(".png", "-white.png");
                imageView.setImage(new Image(imageUrl));
            }
            else {
                button.getStyleClass().remove("active-btn");
                String imageUrl = imageView.getImage().getUrl().replace("-white.png", ".png");
                imageView.setImage(new Image(imageUrl));
            }
        });
    }
}
