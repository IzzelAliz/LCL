package me.kevinwalker.ui;

import javafx.scene.Node;
import me.kevinwalker.main.Main;
import me.kevinwalker.ui.Container;
import me.kevinwalker.ui.Transition;
import me.kevinwalker.ui.controller.FrameController;

import java.util.ArrayList;
import java.util.List;

public class InterfaceManager {

    public static List<Container> containers = new ArrayList<>();

    public static boolean blocked = false;

    public static void addInterface(Container container) {
        FrameController.instance.vertical.getChildren().add(container.getButton());
        container.getButton().setOnMouseClicked(event -> {
            Node current = FrameController.instance.pane.getChildren().get(0);
            if (current.equals(container.getPane()) ||
                    FrameController.instance.pane.getChildren().contains(container.getPane()) || blocked)
                return;
            blocked = true;
            FrameController.instance.pane.getChildren().add(container.getPane());
            InterfaceManager.containers.forEach(obj -> {
                obj.getButton().setStyle("-fx-border-style: solid;" +
                        "-fx-border-width: 0px 0px 0px 10px;" +
                        "-fx-border-color: rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0);");
            });
            container.getButton().setStyle("-fx-border-style: solid;" +
                    "-fx-border-width: 0px 0px 0px 10px;" +
                    "-fx-border-color: rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(" + Main.awtColor.getRed() + "," + Main.awtColor.getGreen() + "," + Main.awtColor.getBlue()+");");

            Transition.playRandomPair(container.getPane(), event1 -> {
                    },
                    current, event1 -> {
                        FrameController.instance.pane.getChildren().remove(current);
                        blocked = false;
                    });
        });
        containers.add(container);
    }

}
