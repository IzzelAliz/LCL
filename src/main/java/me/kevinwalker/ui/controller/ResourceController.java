package me.kevinwalker.ui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.kevinwalker.utils.io.McbbsParser;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResourceController implements Initializable {
    private static ResourceController instance;
    private static ExecutorService service = Executors.newFixedThreadPool(2);

    private boolean digestDisable = true;
    private int forumIndex = 0, sortIndex = 0;
    private static final String[] forums = {"Mod 发布", "材质资源", "皮肤资源", "服务端插件", "地图发布"};
    private static final String[] sorts = {"默认排序", "热门优先", "最新发帖", "查看量", "回复量"};
    @FXML
    Button forumButton, digestButton, sort, refresh;
    @FXML
    TextField page;
    @FXML
    VBox show;

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
        refresh.setOnMouseClicked(event -> fetch());
    }

    private void fetch() {
        service.execute(() -> {
            try {
                String forum = McbbsParser.PARAM.values()[forumIndex].value();
                String sort = McbbsParser.PARAM.values()[sortIndex + McbbsParser.PARAM.DEFAULT.ordinal()].value();
                String page = McbbsParser.PARAM.PAGE.page(Integer.parseInt(this.page.getText()) * 2 - 1);
                String page2 = McbbsParser.PARAM.PAGE.page(Integer.parseInt(this.page.getText()) * 2);
                List<McbbsParser.ThreadPost> list = new ArrayList<>();
                if (this.digestDisable) {
                    list.addAll(McbbsParser.parse(new String[]{forum, sort, page}));
                    list.addAll(McbbsParser.parse(new String[]{forum, sort, page2}));
                } else {
                    list.addAll(McbbsParser.parse(new String[]{forum, sort, page,
                            McbbsParser.PARAM.FILTER_DIGEST.value()}));
                    list.addAll(McbbsParser.parse(new String[]{forum, sort, page2,
                            McbbsParser.PARAM.FILTER_DIGEST.value()}));
                }
                Platform.runLater(() -> show.getChildren().clear());
                list.forEach(threadPost -> Platform.runLater(() -> {
                    Button button = new Button(threadPost.title);
                    button.setPrefWidth(600);
                    button.setAlignment(Pos.CENTER_LEFT);
                    if (threadPost.color.getBlue() == 0 && threadPost.color.getGreen() == 0
                            && threadPost.color.getRed() == 0)
                        threadPost.color = threadPost.color.invert();
                    button.setStyle("-fx-border-width: 0; -fx-text-fill: " + color(threadPost.color) + ";" +
                            "-fx-background-color: rgba(0,0,0,0.5);" +
                            "-fx-padding: 6.0 12.0 6.0 12.0;");
                    button.setOnMouseClicked(event -> {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                            try {
                                Desktop.getDesktop().browse(new URI(threadPost.url));
                            } catch (IOException | URISyntaxException e) {
                                error(e);
                            }
                    });
                    show.getChildren().add(button);
                }));
            } catch (Exception e) {
                error(e);
            }
        });
    }

    private static String color(Color color) {
        return "#" + Integer.toHexString((int) (color.getRed() * 255)) +
                Integer.toHexString((int) (color.getGreen() * 255)) +
                Integer.toHexString((int) (color.getBlue() * 255));
    }

    private void error(Exception e) {
        Platform.runLater(() -> {
            show.getChildren().clear();
            Text text1 = new Text("哎呀，出错了！");
            text1.setFill(Color.ORANGERED);
            Text text2 = new Text(e.toString());
            text2.setFill(Color.RED);
            show.getChildren().addAll(text1, text2);
        });
    }

}
