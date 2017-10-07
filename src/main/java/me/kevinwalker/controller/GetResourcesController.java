package me.kevinwalker.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/6.
 */
public class GetResourcesController implements Initializable {
    @FXML
    private ScrollPane ResourcesPane;

    @FXML
    private AnchorPane mainGui;

    @FXML
    private SVGPath handsvg;

    @FXML
    private ImageView background;

    @FXML
    private Button closebtn, leave;

    @FXML
    private GridPane pluginPane, texturePane, skinPane;

    @FXML
    private GridPane modPane;

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
        File file = new File(Main.getBaseDir(), "LclConfig/" + Main.json.getString("background"));
        if (file.exists()) {
            try {
                Util.zoomImage("LclConfig/" + Main.json.getString("background"), "LclConfig/" + Main.json.getString("background"), 800, 530);
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

    /**
     * 帖子解析按钮配置
     */
    void Button() {

    }

    boolean mod = false, plugin = false, skin = false, texture = false;

    @FXML
    void onModSelect() {
        mod = !mod;
        if (mod)
            new Thread(new Runnable() {
                int i = 0;

                @Override
                public void run() {
                    List<McbbsParser.ThreadPost> list = new ArrayList<>();
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_MOD.value(), McbbsParser.PARAM.PAGE.page(1)));
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_MOD.value(), McbbsParser.PARAM.PAGE.page(2)));
                    list.stream().forEach(thread -> {
                        Platform.runLater(() -> {
                            HBox box = new HBox();
                            box.setMaxWidth(680.0D);
                            box.setPrefWidth(680.0D);
                            box.setPrefHeight(20.0D);
                            Rectangle rectangle = new Rectangle(30.0D, 40.0D);
                            FadeTransition ft = new FadeTransition(Duration.millis(1000), box);
                            ft.setFromValue(0);
                            ft.setToValue(1.0);
                            ft.setCycleCount(1);
                            ft.setAutoReverse(true);
                            if (thread.digest) {
                                rectangle.setFill(new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 0.5));
                            } else {
                                if (thread.reply > 300)
                                    rectangle.setFill(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 0.5));
                                else if (thread.reply > 200)
                                    rectangle.setFill(new Color(Color.LEMONCHIFFON.getRed(), Color.LEMONCHIFFON.getGreen(), Color.LEMONCHIFFON.getBlue(), 0.5));
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
                            modPane.getChildren().add(box);
                            ft.play();
                        });
                    });
                }
            }).start();
        else
            modPane.getChildren().clear();

    }

    @FXML
    void onPluginSelect() {
        plugin = !plugin;
        if (plugin)
            new Thread(new Runnable() {
                int i = 0;

                @Override
                public void run() {
                    List<McbbsParser.ThreadPost> list = new ArrayList<>();
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_PLUGIN.value(), McbbsParser.PARAM.PAGE.page(1)));
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_PLUGIN.value(), McbbsParser.PARAM.PAGE.page(2)));
                    list.stream().forEach(thread -> {
                        Platform.runLater(() -> {
                            HBox box = new HBox();
                            box.setMaxWidth(680.0D);
                            box.setPrefWidth(680.0D);
                            box.setPrefHeight(20.0D);
                            Rectangle rectangle = new Rectangle(30.0D, 40.0D);
                            FadeTransition ft = new FadeTransition(Duration.millis(1000), box);
                            ft.setFromValue(0);
                            ft.setToValue(1.0);
                            ft.setCycleCount(1);
                            ft.setAutoReverse(true);
                            if (thread.digest) {
                                rectangle.setFill(new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 0.5));
                            } else {
                                if (thread.reply > 300)
                                    rectangle.setFill(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 0.5));
                                else if (thread.reply > 200)
                                    rectangle.setFill(new Color(Color.LEMONCHIFFON.getRed(), Color.LEMONCHIFFON.getGreen(), Color.LEMONCHIFFON.getBlue(), 0.5));
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
                            pluginPane.getChildren().add(box);
                            ft.play();
                        });
                    });
                }
            }).start();
        else
            pluginPane.getChildren().clear();
    }

    @FXML
    void onSkinSelect() {
        skin = !skin;
        if (skin)
            new Thread(new Runnable() {
                int i = 0;

                @Override
                public void run() {
                    List<McbbsParser.ThreadPost> list = new ArrayList<>();
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_SKIN.value(), McbbsParser.PARAM.PAGE.page(1)));
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_SKIN.value(), McbbsParser.PARAM.PAGE.page(2)));
                    list.stream().forEach(thread -> {
                        Platform.runLater(() -> {
                            HBox box = new HBox();
                            box.setMaxWidth(680.0D);
                            box.setPrefWidth(680.0D);
                            box.setPrefHeight(20.0D);
                            Rectangle rectangle = new Rectangle(30.0D, 40.0D);
                            FadeTransition ft = new FadeTransition(Duration.millis(1000), box);
                            ft.setFromValue(0);
                            ft.setToValue(1.0);
                            ft.setCycleCount(1);
                            ft.setAutoReverse(true);
                            if (thread.digest) {
                                rectangle.setFill(new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 0.5));
                            } else {
                                if (thread.reply > 300)
                                    rectangle.setFill(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 0.5));
                                else if (thread.reply > 200)
                                    rectangle.setFill(new Color(Color.LEMONCHIFFON.getRed(), Color.LEMONCHIFFON.getGreen(), Color.LEMONCHIFFON.getBlue(), 0.5));
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
                            skinPane.getChildren().add(box);
                            ft.play();
                        });
                    });
                }
            }).start();
        else
            skinPane.getChildren().clear();
    }

    @FXML
    void onTextureSelect() {
        texture = !texture;
        if (texture)
            new Thread(new Runnable() {
                int i = 0;

                @Override
                public void run() {
                    List<McbbsParser.ThreadPost> list = new ArrayList<>();
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_TEXTURE.value(), McbbsParser.PARAM.PAGE.page(1)));
                    list.addAll(McbbsParser.parse(McbbsParser.PARAM.FORUM_TEXTURE.value(), McbbsParser.PARAM.PAGE.page(2)));
                    list.stream().forEach(thread -> {
                        Platform.runLater(() -> {
                            HBox box = new HBox();
                            box.setMaxWidth(680.0D);
                            box.setPrefWidth(680.0D);
                            box.setPrefHeight(20.0D);
                            Rectangle rectangle = new Rectangle(30.0D, 40.0D);
                            FadeTransition ft = new FadeTransition(Duration.millis(1000), box);
                            ft.setFromValue(0);
                            ft.setToValue(1.0);
                            ft.setCycleCount(1);
                            ft.setAutoReverse(true);
                            if (thread.digest) {
                                rectangle.setFill(new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 0.5));
                            } else {
                                if (thread.reply > 300)
                                    rectangle.setFill(new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 0.5));
                                else if (thread.reply > 200)
                                    rectangle.setFill(new Color(Color.LEMONCHIFFON.getRed(), Color.LEMONCHIFFON.getGreen(), Color.LEMONCHIFFON.getBlue(), 0.5));
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
                            texturePane.getChildren().add(box);
                            ft.play();
                        });
                    });
                }
            }).start();
        else
            texturePane.getChildren().clear();
    }
}
