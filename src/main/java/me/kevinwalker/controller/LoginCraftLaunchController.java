package me.kevinwalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.Main;
import me.kevinwalker.threads.MusicPlayThread;
import me.kevinwalker.utils.ColorTranslated;
import me.kevinwalker.utils.GetMainColor;
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
    private Label title;

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

        //启动按钮点击
        launch.setOnAction(oa -> {
            try {
                musicPlayer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
     * 界面配置
     */
    public void GuiSetStyle() {

        //设置背景颜色
        File file = new File("LclConfig/background.png");
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
        if (file.exists()) {
            this.BackGroundRGB = GetMainColor.getImagePixel(file);
            try {
                Image image = new Image(file.toURI().toURL().toString(), true);
                background.setFitWidth(800);
                background.setFitHeight(530);
                background.setImage(image);
                String color = ColorTranslated.toHexFromColor(ColorTranslated.Color2Contrary2(ColorTranslated.Color2Contrary2(this.BackGroundRGB)));
                title.setTextFill(Color.web(color));
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
        File bgm = new File("LclConfig/bgm.mp3");
        if (bgm.exists()) {
            musicPlayer = new MusicPlayThread(bgm.getPath());
        } else {
            java.net.URL fileURL = this.getClass().getResource("/css/music/bgm.mp3");
            musicPlayer = new MusicPlayThread(fileURL.getPath());
        }
        musicPlayer.start();

        //设置标题栏颜色
        handsvg.setStyle("-fx-fill:rgba(" + this.BackGroundRGB.getRed() + "," + this.BackGroundRGB.getGreen() + "," + this.BackGroundRGB.getBlue() + ",85%);");

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
