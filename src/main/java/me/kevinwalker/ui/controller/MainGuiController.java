package me.kevinwalker.ui.controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import me.kevinwalker.ui.Container;

public class MainGuiController implements Container {

    @Override
    public Node getPane() {
        return new Pane();
    }

    @Override
    public Node getButton() {
        Button button = new Button("主界面");
        button.setPrefSize(200,50);
        return button;
    }
}
