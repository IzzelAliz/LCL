package me.kevinwalker.ui.transition;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SliceTransition {

    public static void sliceOutRight(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 0, 0, 30, 0, 1.0, handler);
    }

    public static void sliceInRight(Node node, EventHandler<ActionEvent> handler) {
        slice(node, -30, 0, 0, 0, 0.0, handler);
    }

    public static void sliceOutLeft(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 0, 0, -30, 0, 1.0, handler);
    }

    public static void sliceInLeft(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 30, 0, 0, 0, 0.0, handler);
    }

    public static void sliceOutDown(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 0, 0, 0, 30, 1.0, handler);
    }

    public static void sliceInDown(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 0, -30, 0, 0, 0.0, handler);
    }

    public static void sliceOutUp(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 0, 0, 0, -30, 1.0, handler);
    }

    public static void sliceInUp(Node node, EventHandler<ActionEvent> handler) {
        slice(node, 0, -30, 0, 0, 0.0, handler);
    }

    private static void slice(Node node, double layoutFromX, double layoutFromY, double layoutToX, double layoutToY,
                              double opacity, EventHandler<ActionEvent> handler) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), node);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(1D - opacity);
        Path path = new Path();
        path.getElements().add(new MoveTo(node.getLayoutX() + layoutToX, node.getLayoutY() + layoutToY));
        node.setLayoutX(node.getLayoutX() + layoutFromX);
        node.setLayoutY(node.getLayoutY() + layoutFromY);
        PathTransition pathTransition = new PathTransition(Duration.millis(200), path, node);
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, pathTransition);
        parallelTransition.setOnFinished(handler);
        parallelTransition.play();
    }

}
