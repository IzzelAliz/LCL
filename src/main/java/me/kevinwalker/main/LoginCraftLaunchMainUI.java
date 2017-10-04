package me.kevinwalker.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/9/16.
 */
public class LoginCraftLaunchMainUI implements Initializable {

    @FXML
    private Button mybutton;

    @FXML
    private Button mybutton2;

    @FXML
    private ScrollPane Spane;

    @FXML
    private AnchorPane Apane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Spane.setStyle("-fx-background:transparent;");
//        Apane.setStyle("-fx-background:transparent;");
    }
    public void onButtonAction(ActionEvent event){
        System.out.println("按键被单击");
    }
}
