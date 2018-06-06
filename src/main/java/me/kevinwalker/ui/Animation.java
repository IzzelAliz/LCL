package me.kevinwalker.ui;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * 动画效果
 */
public class Animation {
    /**
     * 矩形淡出淡入动画
     *
     * @param ms        动画时间
     * @param fromValue 初始透明度
     * @param toValue   结束透明度
     * @param node      控件
     * @return 动画实例
     */
    public static FadeTransition playFadeTransition(double ms, double fromValue, double toValue, Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(ms), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        return fadeTransition;
    }

    /**
     * 矩形平移动画
     *
     * @param ms         动画时间
     * @param fromXValue 初始X
     * @param toXValue   结束X
     * @param fromYValue 初始Y
     * @param toYValue   结束Y
     * @param node       控件
     * @return 动画实例
     */
    public static TranslateTransition playTranslateTransition(double ms, double fromXValue, double toXValue, double fromYValue, double toYValue, Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(ms), node);
        translateTransition.setFromX(fromXValue);
        translateTransition.setToX(toXValue);
        translateTransition.setFromY(fromYValue);
        translateTransition.setToY(toYValue);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        return translateTransition;
    }

    /**
     * 矩形旋转动画
     *
     * @param ms         动画时间
     * @param byAngle    旋转角度
     * @param cycleCount 重复次数
     * @param node       控件
     * @return 动画实例
     */
    public static RotateTransition playRotateTransition(double ms, double byAngle, int cycleCount, Node node) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(ms), node);
        rotateTransition.setByAngle(byAngle);
        rotateTransition.setCycleCount(cycleCount);
        rotateTransition.setAutoReverse(true);
        return rotateTransition;
    }

    /**
     * 并行动画
     *
     * @param pt       动画组
     * @param timeline 重复次数
     * @return 动画实例
     */
    public static ParallelTransition playParallelTransition(ParallelTransition pt, int timeline) {
        ParallelTransition parallelTransition = pt;
        parallelTransition.setCycleCount(timeline);
        return parallelTransition;
    }

    /**
     * 顺序动画
     *
     * @param st       动画组
     * @param timeline 重复次数
     * @return 动画实例
     */
    public static SequentialTransition playSequentialTransition(SequentialTransition st, int timeline) {
        SequentialTransition sequentialTransition = st;
        sequentialTransition.setCycleCount(timeline);
        return sequentialTransition;
    }

}
