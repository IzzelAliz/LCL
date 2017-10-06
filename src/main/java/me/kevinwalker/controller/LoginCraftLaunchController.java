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
    public static MusicPlayThread musicPlayThread;
    private static File bgm;
    public static boolean musicPlay = true;

    @FXML
    private ImageView background, settingImg, musicImages;

    @FXML
    private Pane MainGui;

    @FXML
    private SVGPath handsvg;

    @FXML
    private Button launch, setting, bulletin, login, resourceManagement, custom, getResources, serverInformation, author, update, music;

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
        music.setOnAction(oa -> {
            if (musicPlay) {
                musicPlayThread.suspend();
                musicPlay = false;
                musicImages.setStyle("-fx-image: url(/css/images/music_close.png)");
            } else {
                musicPlayThread.resume();
                musicImages.setStyle("-fx-image: url(/css/images/music.png)");
                musicPlay = true;
            }
        });

        author.setOnAction(oa -> {
            try {
                Main.author.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setting.setOnAction(oa -> {
            try {
                Main.setting.show();
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
        bgm = new File(Main.getBaseDir(), "LclConfig/" + Main.json.getString("bgm"));
        if (bgm.exists()) {
            musicPlayThread = new MusicPlayThread(bgm.getPath());
        } else {
            Util.saveResource("css/music/bgm.mp3", new File(Main.getBaseDir(), "LclConfig/bgm.mp3"));
            File musicFile = new File(Main.getBaseDir(), "LclConfig/bgm.mp3");
            musicPlayThread = new MusicPlayThread(musicFile.getPath());
        }
        musicPlayThread.start();

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
        button.setStyle("-fx-background-color: rgba(" + red + "," + green + "," + blue + ",0.8);-fx-border-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-width: 1;");
//        button.setStyle("-fx-background-color: linear-gradient(to right,"+ ColorTranslated.toHex(red,green,blue)+","+ColorTranslated.toHex(red2,green2,blue2)+");-fx-opacity:0.9;");
//        button.setStyle("-fx-background-color: linear-gradient(to right,#00fffc,#fff600);-fx-opacity:0.8");
    }
}
