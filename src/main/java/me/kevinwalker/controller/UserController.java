package me.kevinwalker.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.kevinwalker.guis.Transition;
import me.kevinwalker.main.Config;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends MainController {

    public static enum Login {

        Offline("离线"), Yggdrasil("正版"), Custom("自定义");

        private String s;

        Login(String s) {
            this.s = s;
        }

        Login getNext() {
            if (this.ordinal() + 1 == Login.values().length)
                return Login.values()[0];
            else return Login.values()[this.ordinal()+1];
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        LoginCraftLaunchController.onGuiOpen(swap);
        LoginCraftLaunchController.onGuiOpen(input1);
        LoginCraftLaunchController.onGuiOpen(input2);
        LoginCraftLaunchController.onGuiOpen(custom);
        if (Config.instance.authType == null) Config.instance.authType = Login.Offline;
        currentMsg.setText(Config.instance.authType.s);
        if (Config.instance.authType == Login.Offline) {
            input1.setOpacity(1.0D);
            input2.setOpacity(0.0D);
        } else {
            input2.setOpacity(1.0D);
            input1.setOpacity(0.0D);
        }
        swap.setOnMouseClicked(event -> {
            Config.instance.authType = Config.instance.authType.getNext();
            if (Config.instance.authType == Login.Offline) {
                Transition.fadeIn(input1);
                Transition.fadeOut(input2);
                Transition.fadeOut(custom);
            } else if (Config.instance.authType == Login.Yggdrasil){
                Transition.fadeIn(input2);
                Transition.fadeOut(input1);
            } else if (Config.instance.authType == Login.Custom) {
                Transition.fadeIn(custom);
            }
            currentMsg.setText(Config.instance.authType.s);
        });
    }

    @FXML
    public AnchorPane mainGui;

    @FXML
    public Text currentMsg;

    @FXML
    public VBox input1, input2, swap;

    @FXML
    public GridPane custom;

}

