package me.kevinwalker.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import me.kevinwalker.guis.Transition;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.ConfigController;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.PictureUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/7.
 */
public abstract class MainController implements Initializable {
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
            ConfigController.saveJson();
            System.exit(0);
        });
        leave.setOnMouseClicked(event -> {
            Transition.lollipopTransition(leave, Main.mainGui, event.getSceneX(), event.getSceneY(), 1500);
        });
    }

    /**
     * 界面配置
     */
    void guiSetStyle() {
        //设置背景
        File file = new File(Main.getBaseDir(), "LclConfig/" + Config.instance.background);
        if (file.exists()) {
            try {
                PictureUtil.zoomImage("LclConfig/" + Config.instance.background, "LclConfig/" + Config.instance.background, 800, 530);
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