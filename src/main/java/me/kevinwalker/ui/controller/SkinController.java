package me.kevinwalker.ui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import me.kevinwalker.main.Config;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.ZipUtils;
import net.lingala.zip4j.io.ZipInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/7.
 */
public class SkinController implements Initializable {
    public int i = 1;
    List<String> skinList;
    private boolean list = true;

    @FXML
    AnchorPane skinPane;
    @FXML
    Button open;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guisettings();
    }

    /**
     * 界面配置
     */
    private void guisettings() {
        //选择控件获取文件名List
        new Thread(() -> {
            skinList = getFileList(new File(Util.getBaseDir(), "LclConfig/skin"));
            Button skinButton[] = new Button[skinList.size()];
            ImageView[] image = new ImageView[skinList.size()];
            skinPane.setPrefHeight(skinList.size()/2 * 80+160);
            for (int i = 0; i < skinList.size(); i++) {
                try {
                    ZipInputStream inputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), "/LcLConfig/skin/" + skinList.get(i) + ".zip"), "preview.png");
                    ZipInputStream skinInputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), "/LcLConfig/skin/" + skinList.get(i) + ".zip"), "skin.json");
                    Reader reader = new InputStreamReader(skinInputStream, "UTF-8");
                    Gson json = new GsonBuilder().create();
                    SkinText user = json.fromJson(reader, SkinText.class);
                    image[i] = new ImageView(new Image(inputStream));
                    image[i].setFitWidth(60);
                    image[i].setFitHeight(60);
                    skinButton[i] = new Button(skinList.get(i), image[i]);
                    skinButton[i].setStyle("-fx-border-color: #e2e2e2;" +
                            "-fx-border-width: 2.0;");
                    skinButton[i].setPrefSize(250, 80);
                    skinButton[i].setMinSize(250, 80);
                    skinButton[i].setMaxSize(250, 80);
                    if (this.list) skinButton[i].setLayoutX(5);
                    else skinButton[i].setLayoutX(35 + 250);
                    if (this.list) skinButton[i].setLayoutY((i - i / 2) * (80 + 5));
                    else skinButton[i].setLayoutY(((i - 1) - (i - 1) / 2) * (80 + 5));
                    skinButton[i].setContentDisplay(ContentDisplay.LEFT);
                    skinButton[i].setAlignment(Pos.BASELINE_LEFT);
                    skinPane.getChildren().add(skinButton[i]);
                    this.list = !this.list;
                    inputStream.close(true);
                    skinInputStream.close(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final int num = i;
                skinButton[i].setOnMouseClicked(oa -> {
                    Object obj=oa.getSource();
                    if(obj==skinButton[num]){
                        FrameController.instance.setSkin("/LcLConfig/skin/"+skinList.get(num)+".zip");
                    }
//                    Config.instance.skin = ((Text) ((VBox) skinButton[num].getChildren().get(1))
//                            .getChildren().get(0)).getText();
//                    Config.save();
//                    Main.mainGui = new GuiBase("LoginCraftLaunch", Main.primaryStage, 800, 530);
//                    Transition.lollipopTransition(skinButton[num], Main.mainGui, oa.getSceneX(), oa.getSceneY(),
//                            1500);
                });
            }
        }).start();
        open.setStyle("-fx-border-color: #e2e2e2;" +
                "-fx-border-width: 2.0;");
        open.setOnAction(oa -> {
            try {
                java.awt.Desktop.getDesktop().open(new File(Util.getBaseDir(), "LclConfig/skin"));
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
        List<String> result = new ArrayList<>();
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

    public class SkinText {
        private String text;

        public String getText() {
            return text;
        }

    }
}