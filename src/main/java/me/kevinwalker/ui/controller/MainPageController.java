package me.kevinwalker.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import me.kevinwalker.ui.Skin;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    public ImageView background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setImage(Skin.getBackground());
    }

}
