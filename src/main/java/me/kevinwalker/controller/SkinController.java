package me.kevinwalker.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.guis.Transition;
import me.kevinwalker.main.ConfigController;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.Json;
import me.kevinwalker.utils.ZipUtils;
import net.lingala.zip4j.io.ZipInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/7.
 */
public class SkinController extends MainController {
    List<String> skinList;
    private boolean list = true;

    @FXML
    AnchorPane skinPane;
    @FXML
    Button open;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        guisettings();
        LoginCraftLaunchController.onGuiOpen(open);
    }

    /**
     * 界面配置
     */
    private void guisettings() {
        //选择控件获取文件名List
        new Thread(() -> {
            skinList = getFileList(new File(Main.getBaseDir(), "LclConfig/skin"));
            HBox[] skinButton = new HBox[skinList.size()];
            ImageView[] image = new ImageView[skinList.size()];
            skinPane.setPrefHeight(skinList.size() * 205);
            for (int i = 0; i < skinList.size(); i++) {
                ZipUtils zipFile = new ZipUtils(skinList.get(i));
                try {
                    ZipInputStream inputStream = zipFile.getInputStream("preview.png");
                    ZipInputStream skinInputStream = zipFile.getInputStream("skin.json");
                    Json json = new Json(skinInputStream);
                    image[i] = new ImageView(new Image(inputStream));
                    image[i].setFitWidth(80);
                    image[i].setFitHeight(80);
                    VBox vBox = new VBox();
                    Text text;
                    vBox.getChildren().add(text = new Text(skinList.get(i)));
                    text.setFill(Color.WHITE);
                    vBox.getChildren().add(text = new Text(json.getString("text")));
                    text.setFill(Color.WHITE);
                    vBox.setPadding(new Insets(5.0D));
                    vBox.setSpacing(5.0D);
                    vBox.setAlignment(Pos.CENTER_LEFT);
                    skinButton[i] = new HBox(image[i], vBox);
                    skinButton[i].setPadding(new Insets(10.0D));
                    skinButton[i].setSpacing(15.0D);
                    skinButton[i].setAlignment(Pos.CENTER_LEFT);
                    skinButton[i].getStyleClass().add("button");
                    skinButton[i].setPrefSize(330, 100);
                    skinButton[i].setMinSize(330, 100);
                    skinButton[i].setMaxSize(330, 100);
                    if (this.list) skinButton[i].setLayoutX(8);
                    else skinButton[i].setLayoutX(15 + 330);
                    if (this.list) skinButton[i].setLayoutY((i - i / 2) * (100 + 5));
                    else skinButton[i].setLayoutY(((i - 1) - (i - 1) / 2) * (100 + 5));
                    skinPane.getChildren().add(skinButton[i]);
                    LoginCraftLaunchController.onGuiOpen(skinButton[i]);
                    this.list = !this.list;
                    inputStream.close(true);
                    skinInputStream.close(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int num = i;
                skinButton[i].setOnMouseClicked(oa -> {
                    ConfigController.json.put("skin", ((Text) ((VBox) skinButton[num].getChildren().get(1))
                            .getChildren().get(0)).getText());
                    ConfigController.saveJson();
                    Main.mainGui = new GuiBase("LoginCraftLaunch", Main.primaryStage, 800, 530);
                    Transition.lollipopTransition(skinButton[num], Main.mainGui, oa.getSceneX(), oa.getSceneY(),
                            1500);
                });
            }
        }).start();
        open.setOnAction(oa -> {
            try {
                java.awt.Desktop.getDesktop().open(new File(Main.getBaseDir(), "LclConfig/skin"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取材质文件
     *
     * @param file 文件位置
     * @return 文件名List
     */
    public static List<String> getFileList(File file) {
        List<String> result = new ArrayList<String>();
        if (!file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            result.add(file.getAbsolutePath());
        } else {
            File[] directoryList = file.listFiles(file1 -> file1.isFile() && file1.getName().endsWith(".zip"));
            for (File aDirectoryList : directoryList) {
                result.add(aDirectoryList.getName().substring(0, aDirectoryList.getName().length() - 4));
            }
        }
        return result;
    }
}
