package me.kevinwalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.Main;
import me.kevinwalker.threads.MusicPlayThread;
import me.kevinwalker.utils.ColorTranslated;
import me.kevinwalker.utils.GetMainColor;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
    public static MusicPlayThread musicPlayer = new MusicPlayThread();

    @FXML
    private ImageView background;

    @FXML
    private Label title;

    @FXML
    private SVGPath handsvg;

    @FXML
    private Button launch, setting, bulletin, login, resourceManagement, custom, getResources, serverInformation, author, update;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("LclConfig/background.png");
        try {
            zoomImage("LclConfig/background.png", "LclConfig/background.png", 800, 530);
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
        }
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
        buttonSetStyle();
        musicPlayer.start();

        handsvg.setStyle("-fx-fill:rgba(" + this.BackGroundRGB.getRed() + "," + this.BackGroundRGB.getGreen() + "," + this.BackGroundRGB.getBlue() + ",85%);");

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

    public void buttonSetStyle(){

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

    public static void zoomImage(String src, String dest, int w, int h) throws Exception {

        double wr = 0, hr = 0;
        File srcFile = new File(src);
        File destFile = new File(dest);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        java.awt.Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr = w * 1.0 / bufImg.getWidth();     //获取缩放比例
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
