package me.kevinwalker.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by KevinWalker on 2017/10/4.
 */
class GuiBase {
    private double xOffset = 0;
    private double yOffset = 0;
    private Parent root;
    private Scene scene;
    private Stage stage;

    /**
     * new一个新的界面
     *
     * @param fxmlName fxml文件的名称
     * @param stage    显示到的的Stage
     * @param x        界面长度
     * @param y        界面宽度
     */
    public GuiBase(String fxmlName, Stage stage, int x, int y) {
        this.stage = stage;
        try {
            this.root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }/*
        this.root.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        this.root.setOnMouseDragged((MouseEvent event) -> {
            event.consume();
            stage.setX(event.getScreenX() - xOffset);
            if (event.getScreenY() - yOffset < 0) {
                stage.setY(0);
            } else {
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        */
        this.scene = new Scene(root, x, y);
    }

    public GuiBase(String fxmlName, Stage stage) throws Exception {
        this(fxmlName, stage, 800, 500);
    }

    public void show() {
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public Parent getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }
}
