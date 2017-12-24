package me.kevinwalker.ui.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import me.kevinwalker.ui.Container;
import me.kevinwalker.ui.transition.ZoomTransition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterfaceManager {

    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static List<Container> containers = new ArrayList<>();

    public static void addInterface(Container container) {
        FrameController.instance.vertical.getChildren().add(container.getButton());
        container.getButton().setOnMouseClicked(event -> {
            Node current = FrameController.instance.pane.getChildren().get(0);
            if (current.equals(container.getPane()))
                return;
            ZoomTransition.zoomOutExpand(current);
            service.schedule(() -> Platform.runLater(() -> FrameController.instance.pane.getChildren().remove(current)),
                    300, TimeUnit.MILLISECONDS);
            FrameController.instance.pane.getChildren().add(container.getPane());
            ZoomTransition.zoonInContract(container.getPane());
        });
        containers.add(container);
    }

}
