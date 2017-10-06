package me.kevinwalker.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.Util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class SettingController implements Initializable {

    @FXML
    private AnchorPane mainGui;

    @FXML
    private SVGPath handsvg;

    @FXML
    private ImageView background;

    @FXML
    private Button closebtn, leave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mouseAction();
        guiSetStyle();
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

    /**
     * 界面配置
     */
    void guiSetStyle() {
        //设置背景
        File file = new File(Main.getBaseDir(), "LclConfig/"+Main.json.getString("background"));
        if (file.exists()) {
            try {
                Util.zoomImage("LclConfig/"+Main.json.getString("background"), "LclConfig/"+Main.json.getString("background"), 800, 530);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                background.setImage(new Image(file.toURI().toURL().toString(), true));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            mainGui.setStyle("-fx-background-image: url(/css/images/background.jpg)");
        }
        //设置标题栏
        handsvg.setStyle("-fx-fill:rgba(122,122,122,0.9);");
    }
}
