package me.kevinwalker.guis;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import me.kevinwalker.main.Main;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 由海螺为爱发电
 */
public class Transition {

    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    public static Image getFillingImage() {
        return new Image(Main.class.getResourceAsStream("/css/images/LCL.png"));
    }

    /**
     * @param source   点击的按钮/其他
     * @param show     打开的界面
     * @param x        鼠标的相对于当前 Scene 的 X 位置
     * @param y        鼠标的相对于当前 Scene 的 Y 位置
     * @param duration 持续时间
     */
    @SuppressWarnings({"unchecked"})
    public static void lollipopTransition(Node source, GuiBase show, double x, double y, long duration) {
        Circle circle = new Circle(x, y, 50);
        Color color = randomColor();
        circle.setFill(color);
        show.getScene().setFill(color);
        ObservableList<Node> children, target;
        ImageView view;
        try {
            Node parent = source.getParent();
            while (parent != null && parent.getParent() != null)
                parent = parent.getParent();
            if (parent == null) return;
            Method method = parent.getClass().getMethod("getChildren");
            method.setAccessible(true);
            children = (ObservableList<Node>) method.invoke(parent);
            children.add(circle);
            /*
            view = new ImageView(getFillingImage());
            view.setX(show.getScene().getWidth() / 4D);
            view.setY(show.getScene().getHeight() / 4D);
            view.setOpacity(0);
            view.setFitHeight(show.getScene().getHeight() / 2D);
            view.setFitWidth(show.getScene().getWidth() / 2D);
            children.add(view);
            Method method1 = show.getRoot().getClass().getMethod("getChildren");
            method1.setAccessible(true);
            target = (ObservableList<Node>) method1.invoke(show.getRoot());
            */
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ScaleTransition transition = new ScaleTransition(Duration.millis(duration / 2), circle);
        transition.setToX(40F);
        transition.setToY(40F);
        /*
        FadeTransition fadeTransition3 = new FadeTransition(Duration.millis(duration / 2.5), view);
        fadeTransition3.setFromValue(0);
        fadeTransition3.setToValue(0.5);
        */
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration / 3), circle);
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(1.0);
        ParallelTransition parallelTransition = new ParallelTransition(transition, fadeTransition/*, fadeTransition3*/);
        parallelTransition.play();
        ObservableList<Node> finalChildren = children;
        service.schedule(() -> Platform.runLater(() -> {
            //finalChildren.remove(view);
            finalChildren.remove(circle);
            //target.add(view);
            //view.setOpacity(1);
            show.show();
            FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(duration / 2), show.getRoot());
            fadeTransition2.setFromValue(0.0);
            fadeTransition2.setToValue(1.0);
            fadeTransition2.setCycleCount(1);
            //FadeTransition fadeTransition4 = new FadeTransition(Duration.millis(duration / 2), view);
            //fadeTransition4.setFromValue(1.0);
            //fadeTransition4.setToValue(0);
            //fadeTransition4.setCycleCount(1);
            ParallelTransition parallelTransition1 = new ParallelTransition(fadeTransition2/*, fadeTransition4*/);
            parallelTransition1.play();
        }), duration / 2, TimeUnit.MILLISECONDS);
        /*
        service.schedule(() -> Platform.runLater(() -> {
            target.remove(view);
        }), duration, TimeUnit.MILLISECONDS);
        */
    }

    public static Color randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;
        int green = random.nextInt(130) + 50;
        int blue = random.nextInt(120) + 50;
        return Color.rgb(red, green, blue);
    }
}
