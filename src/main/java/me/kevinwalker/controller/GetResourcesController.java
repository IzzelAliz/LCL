package me.kevinwalker.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import me.kevinwalker.guis.net.McbbsParser;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.Util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/6.
 */
public class GetResourcesController extends MainController {
    @FXML
    private ScrollPane ResourcesPane;

    @FXML
    private Button refresh;

    @FXML
    private GridPane pluginPane, texturePane, skinPane;

    @FXML
    private GridPane modPane;

    @FXML
    private CheckBox latest, digest, hot, view;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        image = new Image(GetResourcesController.class.getResourceAsStream("/css/images/loading.gif"));
        iv = new ImageView(image);
    }

    static Image image;
    static ImageView iv;

    boolean mod = false, plugin = false, skin = false, texture = false;

    @FXML
    void onRefresh() {
        if (mod) {
            onModSelect();
            onModSelect();
        }
        if (plugin) {
            onPluginSelect();
            onPluginSelect();
        }
        if (skin) {
            onSkinSelect();
            onSkinSelect();
        }
        if (texture) {
            onTextureSelect();
            onTextureSelect();
        }
    }

    String getFilters() {
        StringBuilder sb = new StringBuilder();
        if (digest.isSelected()) sb.append("&").append(McbbsParser.PARAM.FILTER_DIGEST.value());
        if (hot.isSelected()) sb.append("&").append(McbbsParser.PARAM.FILTER_HEAT.value());
        if (view.isSelected()) sb.append("&").append(McbbsParser.PARAM.FILTER_VIEW.value());
        if (latest.isSelected()) sb.append("&").append(McbbsParser.PARAM.FILTER_LATEST.value());
        if (sb.length() > 0)
            return sb.toString();
        return "";
    }

    @FXML
    void onModSelect() {
        mod = !mod;
        if (mod) {
            new FetchTask(modPane, new String[]{McbbsParser.PARAM.FORUM_MOD.value(), McbbsParser.PARAM.PAGE.page(1) + getFilters()}
                    , new String[]{McbbsParser.PARAM.FORUM_MOD.value(), McbbsParser.PARAM.PAGE.page(2) + getFilters()}).start();
        } else
            modPane.getChildren().clear();
    }

    @FXML
    void onPluginSelect() {
        plugin = !plugin;
        if (plugin)
            new FetchTask(pluginPane, new String[]{McbbsParser.PARAM.FORUM_PLUGIN.value(), McbbsParser.PARAM.PAGE.page(1) + getFilters()}
                    , new String[]{McbbsParser.PARAM.FORUM_PLUGIN.value(), McbbsParser.PARAM.PAGE.page(2) + getFilters()}).start();
        else
            pluginPane.getChildren().clear();
    }

    @FXML
    void onSkinSelect() {
        skin = !skin;
        if (skin)
            new FetchTask(skinPane, new String[]{McbbsParser.PARAM.FORUM_SKIN.value(), McbbsParser.PARAM.PAGE.page(1) + getFilters()}
                    , new String[]{McbbsParser.PARAM.FORUM_SKIN.value(), McbbsParser.PARAM.PAGE.page(2) + getFilters()}).start();
        else
            skinPane.getChildren().clear();
    }

    @FXML
    void onTextureSelect() {
        texture = !texture;
        if (texture)
            new FetchTask(texturePane, new String[]{McbbsParser.PARAM.FORUM_TEXTURE.value(), McbbsParser.PARAM.PAGE.page(1) + getFilters()}
                    , new String[]{McbbsParser.PARAM.FORUM_TEXTURE.value(), McbbsParser.PARAM.PAGE.page(2) + getFilters()}).start();
        else
            texturePane.getChildren().clear();
    }

    public static class FetchTask extends Thread {
        String[][] params;
        GridPane pane;
        int i = 0;

        public FetchTask(GridPane pane, String[]... params) {
            this.params = params;
            this.pane = pane;
        }

        @Override
        public void run() {
            Platform.runLater(() -> {
                pane.setAlignment(Pos.CENTER);
                GridPane.setConstraints(iv, 0, 0);
                pane.getChildren().add(iv);
            });
            List<McbbsParser.ThreadPost> list = new ArrayList<>();
            for (String[] param : params)
                list.addAll(McbbsParser.parse(param));
            Platform.runLater(() -> {
                pane.setAlignment(Pos.BASELINE_LEFT);
                pane.getChildren().clear();
            });
            list.forEach(thread -> {
                Platform.runLater(() -> {
                    HBox box = new HBox();
                    box.setOnMouseClicked(e -> {
                        URI uri = java.net.URI.create(thread.url);
                        Desktop dp = Desktop.getDesktop();
                        if (dp.isSupported(Desktop.Action.BROWSE))
                            try {
                                dp.browse(uri);
                            } catch (IOException ignored) {
                            }
                    });
                    box.setMaxWidth(680.0D);
                    box.setPrefWidth(680.0D);
                    box.setPrefHeight(40.0D);
                    Rectangle rectangle = new Rectangle(20.0D, 40.0D);
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), box);
                    ft.setFromValue(0);
                    ft.setToValue(1.0);
                    ft.setCycleCount(1);
                    ft.setAutoReverse(true);
                    if (thread.digest) {
                        rectangle.setFill(new Color(Color.BLUEVIOLET.getRed(), Color.BLUEVIOLET.getGreen(), Color.BLUEVIOLET.getBlue(), 0.5));
                    } else {
                        if (thread.reply > 300)
                            rectangle.setFill(new Color(Color.ORANGERED.getRed(), Color.ORANGERED.getGreen(), Color.ORANGERED.getBlue(), 0.5));
                        else if (thread.reply > 200)
                            rectangle.setFill(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 0.5));
                        else if (thread.reply > 100)
                            rectangle.setFill(new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), 0.5));
                        else
                            rectangle.setFill(new Color(0.99, 0.99, 0.99, 0.5));
                    }
                    box.getChildren().add(rectangle);
                    Text text = new Text();
                    text.setText("  " + thread.title);
                    text.setFill(thread.color);
                    text.setFont(Font.font("微软雅黑", FontWeight.BOLD, 15));
                    box.getChildren().add(text);
                    box.setAlignment(Pos.CENTER_LEFT);
                    GridPane.setConstraints(box, 0, i++);
                    pane.getChildren().add(box);
                    ft.play();
                });
            });
        }
    }
}
