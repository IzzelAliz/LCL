package me.kevinwalker.ui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Main;
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
    public Text username, titleText;
    @FXML
    public Line l1, l2;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        Color color = null;
        InterfaceManager.containers.forEach(container -> vertical.getChildren().add(container.getButton()));
        InterfaceManager.containers.forEach(container -> {
            container.getButton().setOnMouseClicked(event -> {
                pane.getChildren().clear();
                pane.getChildren().add(container.getPane());
            });
        });
        try (ZipInputStream skinInputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "skin.json")) {
            Reader reader = new InputStreamReader(skinInputStream, "UTF-8");
            Gson json = new GsonBuilder().create();
            GuiColor user = json.fromJson(reader, GuiColor.class);
            java.awt.Color awtColor = ColorTranslated.toColorFromString(user.getColorText());
            color = Color.rgb(awtColor.getRed(),awtColor.getGreen(),awtColor.getBlue(),0.5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleText.setFill(color);
        username.setFill(color);
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
     * 设置按钮图标
     */
    public static void setButtonImage() {
    }

    @FXML
    void onExit() {
        Config.save();
        System.exit(0);
    }

    public class GuiColor {
        private String rad;
        private String green;
        private String blue;
        private String colorText;

        public String getColorText() {
            return colorText;
        }

        public String getRad() {
            return rad;
        }

        public String getGreen() {
            return green;
        }

        public String getBlue() {
            return blue;
        }
    }
}
