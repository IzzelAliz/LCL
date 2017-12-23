package me.kevinwalker.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.kevinwalker.main.Config;
import me.kevinwalker.ui.Skin;
import me.kevinwalker.utils.io.PictureUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public VBox vertical;
    @FXML
    public Pane pane, base;
    @FXML
    public Pane title;
    @FXML
    public ImageView avatar, background;
    @FXML
    public Text username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InterfaceManager.containers.forEach(container -> vertical.getChildren().add(container.getButton()));
        background.setImage(Skin.getBackground());
        background.setFitHeight(500);
        background.setFitWidth(800);
    }

    @FXML
    void onExit() {
        Config.save();
        System.exit(0);
    }

}
