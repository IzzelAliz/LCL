package me.kevinwalker.controller;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.Main;
import me.kevinwalker.threads.MusicPlayThread;
import me.kevinwalker.utils.Util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/9/16.
 */
public class LoginCraftLaunchController implements Initializable {
    private java.awt.Color BackGroundRGB = new java.awt.Color(122, 122, 122);
    public MusicPlayThread musicPlayer;

    @FXML
    private ImageView background;

    @FXML
    private ImageView settingImg;

    @FXML
    private Pane MainGui;

    @FXML
    private SVGPath handsvg;

    @FXML
    private Button launch, setting, bulletin, login, resourceManagement, custom, getResources, serverInformation, author, update;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //设置界面
        GuiSetStyle();
        mouseAction();
        mouseMoved();
    }

    /**
     * 鼠标点击设置
     */
    void mouseAction() {
        //启动按钮点击
        launch.setOnAction(oa -> {
            try {
                musicPlayer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        login.setOnAction(oa -> {
        });

        setting.setOnAction(oa -> {
            try {
                new GuiBase("Setting", Main.stage, 800, 530).show();
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
            RotateTransition rotateTransition =
                    new RotateTransition(Duration.millis(2000), settingImg);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(2);
            rotateTransition.setAutoReverse(true);
            rotateTransition.play();
        });
    }

    /**
     * 界面配置
     */
    void GuiSetStyle() {

        //设置背景颜色
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

        //设置随机按钮颜色部分
        onGuiOpen(launch);
        onGuiOpen(setting);
        onGuiOpen(bulletin);
        onGuiOpen(login);
        onGuiOpen(resourceManagement);
        onGuiOpen(custom);
        onGuiOpen(getResources);
        onGuiOpen(serverInformation);
        onGuiOpen(author);
        onGuiOpen(update);

        //播放音乐
        File bgm = new File(Main.getBaseDir(), "LclConfig/" + Main.json.getString("bgm"));
        System.out.println(Main.json.asMap().get("bgm"));
        if (bgm.exists()) {
            musicPlayer = new MusicPlayThread(bgm.getPath());
        } else {
            File musicFile = new File(Main.getBaseDir(), "LclConfig/bgm.mp3");
            Util.saveResource("css/music/bgm.mp3", musicFile);
            musicPlayer = new MusicPlayThread(musicFile.getPath());
        }
        musicPlayer.start();

        //设置标题栏颜色
        handsvg.setStyle("-fx-fill:rgba(" + this.BackGroundRGB.getRed() + "," + this.BackGroundRGB.getGreen() + "," + this.BackGroundRGB.getBlue() + ",0.9);");
    }

    public void onCloseButtonAction(ActionEvent event) {
        System.exit(0);
    }

    public static void onGuiOpen(Button button) {
        int red;
        int green;
        int blue;
        Random random = new Random();
        red = random.nextInt(150) + 50;
        green = random.nextInt(130) + 50;
        blue = random.nextInt(120) + 50;
        button.setStyle("-fx-background-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-width: 1;");
    }
}
