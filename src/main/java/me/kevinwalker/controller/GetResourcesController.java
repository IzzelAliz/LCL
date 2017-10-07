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
        image = new Image(GetResourcesController.class.getResourceAsStream("/css/images/loading.gif"));
        iv = new ImageView(image);
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

    static Image image;
    static ImageView iv;

    boolean mod = false, plugin = false, skin = false, texture = false;

    @FXML
    void onModSelect() {
        mod = !mod;
        if (mod)
            new FetchTask(modPane, new String[]{McbbsParser.PARAM.FORUM_MOD.value(), McbbsParser.PARAM.PAGE.page(1)}
                    , new String[]{McbbsParser.PARAM.FORUM_MOD.value(), McbbsParser.PARAM.PAGE.page(2)}).start();
        else
            modPane.getChildren().clear();

    }

    @FXML
    void onPluginSelect() {
        plugin = !plugin;
        if (plugin)
            new FetchTask(pluginPane, new String[]{McbbsParser.PARAM.FORUM_PLUGIN.value(), McbbsParser.PARAM.PAGE.page(1)}
                    , new String[]{McbbsParser.PARAM.FORUM_PLUGIN.value(), McbbsParser.PARAM.PAGE.page(2)}).start();
        else
            pluginPane.getChildren().clear();
    }

    @FXML
    void onSkinSelect() {
        skin = !skin;
        if (skin)
            new FetchTask(skinPane, new String[]{McbbsParser.PARAM.FORUM_SKIN.value(), McbbsParser.PARAM.PAGE.page(1)}
                    , new String[]{McbbsParser.PARAM.FORUM_SKIN.value(), McbbsParser.PARAM.PAGE.page(2)}).start();
        else
            texturePane.getChildren().clear();
    }

    @FXML
    void onTextureSelect() {
        texture = !texture;
        if (texture)
            new FetchTask(texturePane, new String[]{McbbsParser.PARAM.FORUM_TEXTURE.value(), McbbsParser.PARAM.PAGE.page(1)}
                    , new String[]{McbbsParser.PARAM.FORUM_TEXTURE.value(), McbbsParser.PARAM.PAGE.page(2)}).start();
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
            list.stream().forEach(thread -> {
                Platform.runLater(() -> {
                    HBox box = new HBox();
                    box.setOnMouseClicked(e -> {
                        URI uri = java.net.URI.create(thread.url);
                        Desktop dp = Desktop.getDesktop();
                        if (dp.isSupported(Desktop.Action.BROWSE))
                            try {
                                dp.browse(uri);
                            } catch (IOException e1) {
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
