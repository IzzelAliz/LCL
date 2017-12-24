package me.kevinwalker.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Main;
import me.kevinwalker.ui.Skin;
import me.kevinwalker.utils.ColorTranslated;
import me.kevinwalker.utils.GetMainColor;

import java.net.URL;
import java.util.ResourceBundle;

public class FrameController implements Initializable {

    public static FrameController instance;

    @FXML
    public VBox vertical;
    @FXML
    public Pane pane, base;
    @FXML
    public Pane title, hover;
    @FXML
    public ImageView avatar, background;
    @FXML
    public Text username, titleText;
    @FXML
    public Line l1, l2;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        InterfaceManager.containers.forEach(container -> vertical.getChildren().add(container.getButton()));
        InterfaceManager.containers.forEach(container -> {
            container.getButton().setOnMouseClicked(event -> {
                pane.getChildren().clear();
                pane.getChildren().add(container.getPane());
            });
        });
        Color color = ColorTranslated.Color2Contrary2(GetMainColor.getImagePixel(Skin.getBackgroundInputStream())).invert();
        titleText.setFill(color);
        username.setFill(color);
        l1.setStroke(color);
        l2.setStroke(color);
        background.setImage(Skin.getBackground());
        background.setFitHeight(500);
        background.setFitWidth(800);
        title.setOnMousePressed(event -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        title.setOnMouseDragged(event -> {
            event.consume();
            Main.primaryStage.setX(event.getScreenX() - xOffset);
            if (event.getScreenY() - yOffset < 0) {
                Main.primaryStage.setY(0);
            } else {
                Main.primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    @FXML
    void onExit() {
        Config.save();
        System.exit(0);
    }

}
