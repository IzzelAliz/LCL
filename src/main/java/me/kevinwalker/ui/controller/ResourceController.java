package me.kevinwalker.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ResourceController implements Initializable {
    private static ResourceController instance;

    private boolean digestDisable = true;
    private int forumIndex = 0, sortIndex = 0;
    private static final String[] forums = {"Mod 发布", "材质资源", "皮肤资源", "服务端插件", "地图发布"};
    private static final String[] sorts = {"默认排序", "热门优先", "最新发帖", "查看量", "回复量"};
    @FXML
    Button forumButton, digestButton, sort;
    @FXML
    TextField page;
    @FXML
    ScrollPane show;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        digestButton.setStyle("-fx-opacity: 0.4;" +
                " -fx-background-color: #1d1d1d;" +
                " -fx-text-fill: #e2e2e2;" +
                " -fx-border-color: #778899;" +
                " -fx-border-width: 2.0;");
        digestButton.setOnMouseClicked(event -> {
            digestDisable = !digestDisable;
            if (digestDisable) digestButton.setStyle("-fx-opacity: 0.4;" +
                    " -fx-background-color: #1d1d1d;" +
                    " -fx-text-fill: #e2e2e2;" +
                    " -fx-border-color: #778899;" +
                    " -fx-border-width: 2.0;");
            else digestButton.setStyle("");
        });
        forumButton.setOnMouseClicked(event -> {
            forumIndex = forumIndex == 4 ? 0 : ++forumIndex;
            forumButton.setText(forums[forumIndex]);
        });
        sort.setOnMouseClicked(event -> {
            sortIndex = sortIndex == 4 ? 0 : ++sortIndex;
            sort.setText(sorts[sortIndex]);
        });
    }

    private static void fetch() {

    }

}
