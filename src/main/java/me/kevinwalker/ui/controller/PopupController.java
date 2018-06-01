package me.kevinwalker.ui.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import me.kevinwalker.main.ImageResourc;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {
    public static PopupController instance;
    public Label message;
    public Button cancel;
    public ImageView error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        if (ImageResourc.error != null)
            error.setImage(ImageResourc.error);
        else error.setImage(ImageResourc.loading);
    }

}

