package me.kevinwalker.ui.transition;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ZoomTransition {

    public static void zoomOutContract(Node node) {
        zoom(node, 1.0, 0.8, 1.0);
    }

    public static void zoomOutExpand(Node node) {
        zoom(node, 1.0, 1.2, 1.0);
    }

    public static void zoonInContract(Node node) {
        zoom(node, 1.2, 1.0, 0.0);
    }

    public static void zoomInExpand(Node node) {
        zoom(node, 0.8, 1.0, 0.0);
    }

    private static void zoom(Node node, double scaleFrom, double scaleTo, double opacity) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), node);
        scaleTransition.setToX(scaleTo);
        scaleTransition.setToY(scaleTo);
        scaleTransition.setFromX(scaleFrom);
        scaleTransition.setFromY(scaleFrom);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(1D - opacity);
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        parallelTransition.play();
    }

}
