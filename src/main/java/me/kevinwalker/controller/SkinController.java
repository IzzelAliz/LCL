package me.kevinwalker.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.ConfigController;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.ZipUtils;

import java.io.File;
import java.io.FileFilter;
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
        skinList = getFileList(new File(Main.getBaseDir(), "LclConfig/skin"));
        Button skinButton[] = new Button[skinList.size()];
        ImageView image[] = new ImageView[skinList.size()];
        skinPane.setPrefHeight(skinList.size() * 205);
        for (int i = 0; i < skinList.size(); i++) {
            ZipUtils zipFile = new ZipUtils(skinList.get(i));
            try (InputStream inputStream = zipFile.getInputStream("preview.png")) {
                image[i] = new ImageView(new Image(inputStream));
                skinButton[i] = new Button(skinList.get(i), image[i]);
                image[i].setFitWidth(80);
                image[i].setFitHeight(80);
                skinButton[i].setPrefSize(330, 100);
                skinButton[i].setMinSize(330, 100);
                skinButton[i].setMaxSize(330, 100);
                if(this.list) {
                    skinButton[i].setLayoutX(8);
                }else{
                    skinButton[i].setLayoutX(15+330);
                }
                if(this.list) {
                    skinButton[i].setLayoutY((i-i/2) * (100 + 5));

                }else{
                    skinButton[i].setLayoutY(((i-1)-(i-1)/2) * (100 + 5));
                }
                skinButton[i].setContentDisplay(ContentDisplay.LEFT);
                skinButton[i].setAlignment(Pos.BASELINE_LEFT);
                skinPane.getChildren().add(skinButton[i]);
                LoginCraftLaunchController.onGuiOpen(skinButton[i]);
                this.list = !this.list;
            } catch (IOException e) {
                e.printStackTrace();
            }
            final int num = i;
            skinButton[i].setOnAction(oa -> {
                ConfigController.json.put("skin",skinButton[num].getText());
                ConfigController.saveJson();
                Main.mainGui = new GuiBase("LoginCraftLaunch", Main.primaryStage, 800, 530);
                Main.mainGui.show();
            });
            open.setOnAction(oa -> {
                try {
                    java.awt.Desktop.getDesktop().open(new File(Main.getBaseDir(), "LclConfig/skin"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
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
            File[] directoryList = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().indexOf("zip") > -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < directoryList.length; i++) {
                result.add(directoryList[i].getName().substring(0, directoryList[i].getName().length() - 4));
            }
        }
        return result;
    }
}
