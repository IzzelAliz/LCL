package me.kevinwalker.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import me.kevinwalker.main.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/6.
 */
public class AuthorController implements Initializable {
    @FXML
    private Button closebtn,leave;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mouseAction();
    }
    /**
     * 鼠标点击设置
     */
    void mouseAction() {
        closebtn.setOnAction(oa -> {
            System.exit(0);
        });
        leave.setOnAction(oa -> {
            Main.mainGui.show();
        });
    }
}
