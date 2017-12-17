package me.kevinwalker.guis;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 由海螺为爱发电
 */
public class Transition {

    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    /**
     *
     * @param source 点击的按钮/其他
     * @param show 打开的界面
     * @param x 鼠标的相对于当前 Scene 的 X 位置
     * @param y 鼠标的相对于当前 Scene 的 Y 位置
     * @param duration 持续时间
     */
    @SuppressWarnings({"unchecked"})
    public static void lollipopTransition(Node source, GuiBase show, double x, double y, long duration) {
        Circle circle = new Circle(x, y, 50);
        circle.setFill(show.getScene().getFill());
        ObservableList<Node> children;
        try {
            Node parent = source.getParent();
            while (parent != null && parent.getParent() != null)
                parent = parent.getParent();
            if (parent == null) return;
            Method method = parent.getClass().getMethod("getChildren");
            method.setAccessible(true);
            children = (ObservableList<Node>) method.invoke(parent);
            children.add(circle);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ScaleTransition transition = new ScaleTransition(Duration.millis(duration / 2), circle);
        transition.setToX(40F);
        transition.setToY(40F);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration / 3), circle);
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(1.0);
        ParallelTransition parallelTransition = new ParallelTransition(transition, fadeTransition);
        parallelTransition.play();
        ObservableList<Node> finalChildren = children;
        service.schedule(() -> Platform.runLater(() -> {
            FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(duration / 2), show.getRoot());
            fadeTransition2.setFromValue(0.0);
            fadeTransition2.setToValue(1.0);
            fadeTransition2.setCycleCount(1);
            fadeTransition2.play();
            show.show();
            finalChildren.remove(circle);
        }), duration / 2, TimeUnit.MILLISECONDS);
    }
}
