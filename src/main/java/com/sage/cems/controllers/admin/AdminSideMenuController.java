package com.sage.cems.controllers.admin;

import com.sage.cems.views.View;
import com.sage.cems.views.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
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

public class AdminSideMenuController implements Initializable {

    public Button menuButton;
    public Button homeBtn;
    public Button studentsBtn;
    public Button lecturersBtn;
    public Button coursesBtn;
    public Button gradesBtn;
    public Button logoutBtn;
    public VBox menuButtonsVBox;
    public AnchorPane parent;
    private boolean fullSideMenu = true;

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
        logoutBtn.setOnAction(actionEvent -> onLogout());

    }

    private void onLogout(){
        ViewFactory.getInstance().closeStage((Stage) homeBtn.getScene().getWindow());
        ViewFactory.getInstance().showLoginWindow();
    }

    private void updateView(Node node) {
        Button button = (Button) node;

        ViewFactory.getInstance().getCurrentViewProperty().addListener((obs, oldVal, newVal) -> {
            if (!ViewFactory.getInstance().getBackStack().isEmpty())
                return;

            button.getStyleClass().remove("active-btn");
            ImageView imageView = (ImageView) button.getGraphic();

            if (newVal.equals(View.valueOf(button.getId()))) {
                button.getStyleClass().add("active-btn");
                imageView.setImage(new Image(switchBlackWhite(imageView.getImage().getUrl())));
            }
            else {
                button.getStyleClass().remove("active-btn");
                imageView.setImage(new Image(switchWhiteBlack(imageView.getImage().getUrl())));
            }
        });
        button.setOnAction(actionEvent -> {
            ViewFactory.getInstance().backStackSizeProperty().set(0);
            ViewFactory.getInstance().getBackStack().clear();
            ViewFactory.getInstance().getCurrentViewProperty().set(View.valueOf(button.getId()));
        });
    }

    private String switchBlackWhite(String imageUrl) {
        if (imageUrl.contains("-white.png"))
            return imageUrl;
        return imageUrl.replace(".png", "-white.png");
    }

    private String switchWhiteBlack(String imageUrl) {
        if (imageUrl.contains("-white.png"))
            return imageUrl.replace("-white.png", ".png");
        return imageUrl;
    }
}
