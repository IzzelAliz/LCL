package me.kevinwalker.ui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import me.kevinwalker.ui.Animation;
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
    public static SkinController instance;
    List<String> skinList;
    private boolean list = true;
    private boolean clickButton = true;

    @FXML
    AnchorPane skinPane;
    @FXML
    Button open;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance=this;
        list=true;
        clickButton=true;
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
            skinPane.setPrefHeight(skinList.size() / 2 * 135 + 250);

            Button button = new Button("←");
            Button useSkinButton = new Button("使用");
            Label title = new Label();
            Label author = new Label();
            TextArea message = new TextArea();

            button.setLayoutY(-300);
            useSkinButton.setLayoutX(1100);
            useSkinButton.setLayoutY(230);

            title.setStyle("-fx-text-fill: rgba(255,255,255,1)");
            title.setFont(Font.font(40));
            title.setAlignment(Pos.CENTER);
            title.setPrefWidth(600);
            title.setLayoutX(-10);
            title.setLayoutY(10);

            author.setStyle("-fx-text-fill: rgba(255,255,255,1)");
            author.setFont(Font.font(20));
            author.setAlignment(Pos.CENTER);
            author.setPrefWidth(600);
            author.setLayoutX(-10);
            author.setLayoutY(230);

            message.setPrefSize(400, 300);
            message.setLayoutX(-600);
            message.setLayoutY(280);

            skinPane.getChildren().add(author);
            skinPane.getChildren().add(title);
            skinPane.getChildren().add(button);
            skinPane.getChildren().add(useSkinButton);
            skinPane.getChildren().add(message);

            for (int i = 0; i < skinList.size(); i++) {
                FrameController.SkinData user = null;
                try {
                    ZipInputStream inputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), "/LcLConfig/skin/" + skinList.get(i) + ".zip"), "title.png");
                    ZipInputStream skinInputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), "/LcLConfig/skin/" + skinList.get(i) + ".zip"), "skin.json");
                    Reader reader = new InputStreamReader(skinInputStream, "UTF-8");
                    Gson json = new GsonBuilder().create();
                    user = json.fromJson(reader, FrameController.SkinData.class);
                    image[i] = new ImageView(new Image(inputStream));
                    skinButton[i] = new Button(skinList.get(i), image[i]);
                    skinButton[i].setStyle("-fx-border-width: 1;" +
                            "-fx-background-color: rgba(29,29,29,0.5);" +
                            "-fx-background-radius: 8;" +
                            "-fx-border-radius: 8;");
                    skinButton[i].setPrefSize(250, 135);
                    skinButton[i].setMinSize(250, 135);
                    skinButton[i].setMaxSize(250, 135);
                    if (this.list) skinButton[i].setLayoutX(20);
                    else skinButton[i].setLayoutX(40 + 250);
                    if (this.list) skinButton[i].setLayoutY((i - i / 2) * (135 + 10));
                    else skinButton[i].setLayoutY(((i - 1) - (i - 1) / 2) * (135 + 10));
                    skinButton[i].setContentDisplay(ContentDisplay.TOP);
                    skinButton[i].setAlignment(Pos.CENTER);
                    skinPane.getChildren().add(skinButton[i]);
                    this.list = !this.list;
                    inputStream.close(true);
                    skinInputStream.close(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final int num = i;
                final FrameController.SkinData skinData = user;
                skinButton[i].setOnMouseClicked(oa -> {
                    if (clickButton) {
                        clickButton = false;
                        for (Button b : skinButton) {
                            if (b != skinButton[num]) {
                                Animation.playTranslateTransition(1000, 0, 600, 0, 0, b).play();
                            } else {
                                author.setText(skinData.author);
                                title.setText(skinList.get(num));
                                message.setText(skinData.message);

                                Animation.playTranslateTransition(700, 0, 165 - b.getLayoutX(), 0, 80 - b.getLayoutY(), b).play();
                                Animation.playTranslateTransition(700, 0, 692, 0, 0, message).play();
                                Animation.playTranslateTransition(700, 0, 0, 0, 300, button).play();
                                Animation.playTranslateTransition(700, 0, -600, 0, 0, useSkinButton).play();
                                Animation.playFadeTransition(900, 0f, 1.0f, title).play();
                                Animation.playFadeTransition(900, 0f, 1.0f, author).play();
                                Animation.playFadeTransition(900, 0f, 1.0f, button).play();
                                Animation.playFadeTransition(900, 0f, 1.0f, message).play();

                                button.setOnMouseClicked(exit -> {
                                    clickButton = true;
                                    for (Button button1 : skinButton) {
                                        Animation.playTranslateTransition(800, 600, 0, 0, 0, button1).play();
                                    }
                                    Animation.playTranslateTransition(1000, 0, -692, 0, 0, message).play();
                                    Animation.playTranslateTransition(600, 0, 0, 0, -300, button).play();
                                    Animation.playTranslateTransition(800, 0, 600, 0, 0, useSkinButton).play();
                                    Animation.playFadeTransition(400, 1.0f, 0f, title).play();
                                    Animation.playFadeTransition(400, 1.0f, 0f, author).play();
                                });

                                useSkinButton.setOnMouseClicked(exit -> {
                                    Object obj = oa.getSource();
                                    if (obj == skinButton[num]) {
                                        FrameController.instance.setSkin("/LcLConfig/skin/" + skinList.get(num) + ".zip");
                                    }
                                });
                            }
                        }
                    }
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
}