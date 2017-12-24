package me.kevinwalker.ui;


import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import me.kevinwalker.ui.controller.FrameController;
import me.kevinwalker.ui.transition.SliceTransition;
import me.kevinwalker.ui.transition.ZoomTransition;
import me.kevinwalker.utils.SimplexNoise;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Transition {
    private static Random random = new Random();
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    private static int r = random.nextInt(10000), g = random.nextInt(10000),
            b = random.nextInt(10000);

    public static void playRandomPair(Node in, EventHandler<ActionEvent> handlerIn, Node out,
                                      EventHandler<ActionEvent> handlerOut) {
        switch (random.nextInt(2)) {
            case 0:
                ZoomTransition.zoonInContract(in, handlerIn);
                ZoomTransition.zoomOutExpand(out, handlerOut);
                break;
            case 1:
                ZoomTransition.zoomInExpand(in, handlerIn);
                ZoomTransition.zoomOutContract(out, handlerOut);
                break;
                /*
            case 2:
                SliceTransition.sliceInDown(in, handlerIn);
                SliceTransition.sliceOutUp(out, handlerOut);
                break;
            case 3:
                SliceTransition.sliceInUp(in, handlerIn);
                SliceTransition.sliceOutDown(out, handlerOut);
                break;
            case 4:
                SliceTransition.sliceInLeft(in, handlerIn);
                SliceTransition.sliceOutRight(out, handlerOut);
                break;
            case 5:
                SliceTransition.sliceInRight(in, handlerIn);
                SliceTransition.sliceOutLeft(out, handlerOut);
                break;
                */
        }
    }

    public static void lollipop(double x, double y) {
        Circle circle = new Circle(x, y, 2);
        circle.setFill(randomColor());
        FrameController.instance.hover.getChildren().add(circle);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), circle);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(2);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000), circle);
        scaleTransition.setToX(10.0);
        scaleTransition.setToY(10.0);
        scaleTransition.setCycleCount(1);
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        parallelTransition.play();
        executorService.schedule(() -> Platform.runLater(() ->
                FrameController.instance.hover.getChildren().remove(circle)), 1000, TimeUnit.MILLISECONDS);
    }

    private static Color randomColor() {
        int red = (int) (170D + 75D * SimplexNoise.noise(((double) r++) / 5D, 0));
        int green = (int) (165D + 70D * SimplexNoise.noise(((double) g++) / 5D, 0));
        int blue = (int) (160D + 65D * SimplexNoise.noise(((double) b++) / 5D, 0));
        return Color.rgb(red, green, blue);
    }
}
