package me.kevinwalker.ui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Locale;
import me.kevinwalker.main.Main;
import me.kevinwalker.ui.InterfaceManager;
import me.kevinwalker.ui.Popup;
import me.kevinwalker.ui.Skin;
import me.kevinwalker.utils.ColorTranslated;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.ZipUtils;
import net.lingala.zip4j.io.ZipInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

public class FrameController implements Initializable {

    public static FrameController instance;

    @FXML
    public VBox vertical;
    @FXML
    public Pane pane, base;
    @FXML
    public Pane title, hover;
    @FXML
    public AnchorPane apane;
    @FXML
    public ImageView avatar, background;
    @FXML
    public Text titleText;
    @FXML
    public Label username;
    @FXML
    public Line l1, l2;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;

        username.setText(Config.instance.name);

        InterfaceManager.containers.forEach(container -> vertical.getChildren().add(container.getButton()));
        InterfaceManager.containers.forEach(container -> {
            container.getButton().setOnMouseClicked(event -> {
                pane.getChildren().clear();
                pane.getChildren().add(container.getPane());
            });
        });

        Color color = null;
        java.awt.Color awtColor = null;

        try (ZipInputStream skinInputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "skin.json")) {
            Reader reader = new InputStreamReader(skinInputStream, "UTF-8");
            Gson json = new GsonBuilder().create();
            SkinData user = json.fromJson(reader, SkinData.class);
            awtColor = ColorTranslated.toColorFromString(user.colorText);
            color = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), 0.8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleText.setFill(color);
        username.setStyle("-fx-text-fill: rgba(" + awtColor.getRed() + "," + awtColor.getGreen() + "," + awtColor.getBlue() + ",0.8);");
        l1.setStroke(color);
        l2.setStroke(color);

        background.setImage(Skin.getBackground());
        background.setFitHeight(500);
        background.setFitWidth(800);
        title.setOnMousePressed(event -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        title.setOnMouseDragged(event -> {
            event.consume();
            Main.primaryStage.setX(event.getScreenX() - xOffset);
            if (event.getScreenY() - yOffset < 0) {
                Main.primaryStage.setY(0);
            } else {
                Main.primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    /**
     * 切换皮肤
     */
    public void setSkin(String skinPath) {
        Config.instance.skin = skinPath;
        Skin.load();

        try (ZipInputStream skinInputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), skinPath), "skin.json")) {
            Reader reader = new InputStreamReader(skinInputStream, "UTF-8");
            Gson json = new GsonBuilder().create();
            SkinData user = json.fromJson(reader, SkinData.class);
            java.awt.Color awtColor = ColorTranslated.toColorFromString(user.colorText);
            java.awt.Color awtTitleColor = ColorTranslated.toColorFromString(user.colorTitle);
            Color color = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), 0.8);
            titleText.setFill(color);
            username.setStyle("-fx-text-fill: rgba(" + awtColor.getRed() + "," + awtColor.getGreen() + "," + awtColor.getBlue() + ");");
            l1.setStroke(color);
            l2.setStroke(color);

            background.setImage(Skin.getBackground());
            background.setFitHeight(500);
            background.setFitWidth(800);

            InterfaceManager.containers.clear();
            vertical.getChildren().clear();
            pane.getChildren().clear();
            InterfaceManager.containers.forEach(container -> vertical.getChildren().add(container.getButton()));
            InterfaceManager.containers.forEach(container -> {
                container.getButton().setOnMouseClicked(event -> {
                    pane.getChildren().clear();
                    pane.getChildren().add(container.getPane());
                });
            });


            Main.load("MainPage", Locale.instance.MainPage, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "MainPage.png")), awtTitleColor, true);
            Main.load("Settings", Locale.instance.Settings, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Settings.png")), awtTitleColor, false);
            Main.load("ResourceManagement", Locale.instance.ResourceManagement, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ResourceManagement.png")), awtTitleColor, false);
            Main.load("ResourceManagement", Locale.instance.ServerData, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ServerInformation.png")), awtTitleColor, false);
            Main.load("Skin", Locale.instance.Skin, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Skin.png")), awtTitleColor, false);
            Main.load("Resources", Locale.instance.Resources, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "loginImg.png")), awtTitleColor, false);

            Config.instance.skin = skinPath;
            SkinController.clickButton = true;
        } catch (Exception e) {
            SkinController.clickButton = false;
            new Popup().display(Locale.instance.Error, e.toString());
            e.printStackTrace();
        }

    }

    @FXML
    void onExit() {
        Config.save();
        System.exit(0);
    }

    public class SkinData {
        public String author;
        public String colorText;
        public String colorTitle;
        public String message;
    }
}
