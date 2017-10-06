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
 * Created by KevinWalker on 2017/10/6.
 */
public class GetResourcesController implements Initializable {
    @FXML
    private AnchorPane MainGui;

    @FXML
    private SVGPath handsvg;

    @FXML
    private ImageView background;

    @FXML
    private Button closebtn,leave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mouseAction();
        GuiSetStyle();
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
    void GuiSetStyle() {
        //设置背景
        File file = new File(Main.getBaseDir(), "LclConfig/background.png");
        if (file.exists()) {
            try {
                Util.zoomImage("LclConfig/background.png", "LclConfig/background.png", 800, 530);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                background.setImage(new Image(file.toURI().toURL().toString(), true));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            MainGui.setStyle("-fx-background-image: url(/css/images/background.png)");
        }

        //设置标题栏
        handsvg.setStyle("-fx-fill:rgba(122,122,122,0.9);");
    }
}
