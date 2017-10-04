package me.kevinwalker.guis;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class GuiBase {
    private double xOffset = 0;
    private double yOffset = 0;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public GuiBase(String fxmlName, Stage stage, int x, int y) throws Exception {
        this.stage = stage;
        this.root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlName + ".fxml"));
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
        this.scene = new Scene(root, 800, 500);
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
