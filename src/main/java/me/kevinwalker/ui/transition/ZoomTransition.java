package me.kevinwalker.ui.transition;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class ZoomTransition {

    public static void zoomOutContract(Node node, EventHandler<ActionEvent> eventHandler) {
        zoom(node, 1.0, 0.8, 1.0, eventHandler);
    }

    public static void zoomOutExpand(Node node, EventHandler<ActionEvent> eventHandler) {
        zoom(node, 1.0, 1.2, 1.0, eventHandler);
    }

    public static void zoonInContract(Node node, EventHandler<ActionEvent> eventHandler) {
        zoom(node, 1.2, 1.0, 0.0, eventHandler);
    }

    public static void zoomInExpand(Node node, EventHandler<ActionEvent> eventHandler) {
        zoom(node, 0.8, 1.0, 0.0, eventHandler);
    }

    private static void zoom(Node node, double scaleFrom, double scaleTo, double opacity, EventHandler<ActionEvent> eventHandler) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
        scaleTransition.setToX(scaleTo);
        scaleTransition.setToY(scaleTo);
        scaleTransition.setFromX(scaleFrom);
        scaleTransition.setFromY(scaleFrom);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), node);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(1D - opacity);
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        parallelTransition.play();
        parallelTransition.setOnFinished(eventHandler);
    }

}
