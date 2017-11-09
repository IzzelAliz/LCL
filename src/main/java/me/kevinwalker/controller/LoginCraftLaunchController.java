package me.kevinwalker.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.ConfigController;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.ZipUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/9/16.
 */
public class LoginCraftLaunchController implements Initializable {
    public static boolean musicPlay = true;

    @FXML
    private ImageView background, musicImages;

    @FXML
    private Label name;

    @FXML
    private Pane MainGui;

    @FXML
    private SVGPath handsvg;

    @FXML
    private Button skin, setting, login, resourceManagement, custom, getResources, serverInformation, author, update, music;

    @FXML
    private ImageView settingImg, skinImg, getResourcesImg, customImg, authorImg, loginImg, serverInformationImg, resourceManagementImg, updateImg, bulletinImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //设置界面
        guiSetStyle();
        mouseAction();
        mouseMoved();
        setButtomImg();
    }


    /**
     * 鼠标点击设置
     */
    void mouseAction() {
        music.setOnAction(oa -> {
            if (musicPlay) {
                Main.musicPlayThread.suspend();
                musicPlay = false;
                musicImages.setStyle("-fx-image: url(/css/images/music_close.png)");
            } else {
                Main.musicPlayThread.resume();
                musicImages.setStyle("-fx-image: url(/css/images/music.png)");
                musicPlay = true;
            }
        });

        author.setOnAction(oa -> {
            try {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Main.author.getRoot());
                fadeTransition.setFromValue(0.2f);
                fadeTransition.setToValue(1f);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
                Main.author.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setting.setOnAction(oa -> {
            try {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Main.setting.getRoot());
                fadeTransition.setFromValue(0.2f);
                fadeTransition.setToValue(1f);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
                Main.setting.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        getResources.setOnAction(oa -> {
            try {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), Main.getResources.getRoot());
                fadeTransition.setFromValue(0.2f);
                fadeTransition.setToValue(1f);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
                Main.getResources.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        skin.setOnAction(oa -> {
            try {
                GuiBase skin = new GuiBase("Skin", Main.primaryStage, 800, 530);
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), skin.getRoot());
                fadeTransition.setFromValue(0.2f);
                fadeTransition.setToValue(1f);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
                skin.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 鼠标悬停动画设置
     */
    void mouseMoved() {
        setting.setOnMouseMoved(omm -> {
            rotateTransition(settingImg);
        });

        update.setOnMouseMoved(omm -> {
            rotateTransition(updateImg);
        });


        skin.setOnMouseMoved(omm -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000), skinImg);
            scaleTransition.setToX(1.3f);
            scaleTransition.setToY(1.3f);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);
            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(1000), skinImg);
            scaleTransition2.setToX(1f);
            scaleTransition2.setToY(1f);
            scaleTransition2.setCycleCount(1);
            scaleTransition2.setAutoReverse(true);
            SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition, scaleTransition2);
            sequentialTransition.setCycleCount(1);
            sequentialTransition.play();
        });
    }

    private void rotateTransition(Node node) {
        RotateTransition rotateTransitionSetting =
                new RotateTransition(Duration.millis(1000), node);
        rotateTransitionSetting.setByAngle(180f);
        rotateTransitionSetting.setCycleCount(1);
        rotateTransitionSetting.setAutoReverse(true);
        rotateTransitionSetting.play();
    }

    /**
     * 界面配置
     */
    void guiSetStyle() {

        //设置背景
        File file = new File(Main.getBaseDir(), "LclConfig/" + ConfigController.json.getString("background"));
        if (file.exists()) {
            try {
                Util.zoomImage("LclConfig/" + ConfigController.json.getString("background"), "LclConfig/" + ConfigController.json.getString("background"), 800, 530);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                background.setImage(new Image(file.toURI().toURL().toString(), true));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            MainGui.setStyle("-fx-background-image: url(/css/images/background.jpg)");
        }


        //设置随机按钮颜色部分
        onGuiOpen(skin);
        onGuiOpen(setting);
        onGuiOpen(login);
        onGuiOpen(resourceManagement);
        onGuiOpen(custom);
        onGuiOpen(getResources);
        onGuiOpen(serverInformation);
        onGuiOpen(author);
        onGuiOpen(update);

        //设置标题栏
        name.setText(ConfigController.json.getString("name"));
//        handsvg.setStyle("-fx-fill:rgba(" + this.BackGroundRGB.getRed() + "," + this.BackGroundRGB.getGreen() + "," + this.BackGroundRGB.getBlue() + ",0.9);");
        handsvg.setStyle("-fx-fill:rgba(122,122,122,0.9);");

    }

    public void setButtomImg() {
        ZipUtils zipFile = new ZipUtils(ConfigController.json.getString("skin"));
        settingImg.setImage(new Image(zipFile.getInputStream("settingImg.png")));
        skinImg.setImage(new Image(zipFile.getInputStream("skinImg.png")));
        getResourcesImg.setImage(new Image(zipFile.getInputStream("getResourcesImg.png")));
        customImg.setImage(new Image(zipFile.getInputStream("customImg.png")));
        authorImg.setImage(new Image(zipFile.getInputStream("authorImg.png")));
        loginImg.setImage(new Image(zipFile.getInputStream("loginImg.png")));
        serverInformationImg.setImage(new Image(zipFile.getInputStream("serverInformationImg.png")));
        resourceManagementImg.setImage(new Image(zipFile.getInputStream("resourceManagementImg.png")));
        updateImg.setImage(new Image(zipFile.getInputStream("updateImg.png")));
    }

    public void onCloseButtonAction(ActionEvent event) {
        System.exit(0);
    }

    public static void onGuiOpen(Button button) {
        int red;
        int green;
        int blue;
//        int red2;
//        int green2;
//        int blue2;
        Random random = new Random();
//        Random random2 = new Random();
        red = random.nextInt(150) + 50;
        green = random.nextInt(130) + 50;
        blue = random.nextInt(120) + 50;
//        red2 = random.nextInt(150) + 50;
//        green2 = random.nextInt(130) + 50;
//        blue2 = random.nextInt(120) + 50;
        button.setStyle("-fx-background-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-width: 1;");
//        button.setStyle("-fx-background-color: linear-gradient(to right,"+ ColorTranslated.toHex(red,green,blue)+","+ColorTranslated.toHex(red2,green2,blue2)+");-fx-opacity:0.9;");
//        button.setStyle("-fx-background-color: linear-gradient(to right,#00fffc,#fff600);-fx-opacity:0.8;");
    }

}
