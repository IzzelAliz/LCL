package me.kevinwalker.utils;

import me.kevinwalker.main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by KevinWalker on 2017/10/5.
 */
public class PictureUtil {
    /**
     * 缩放图片
     *
     * @param src  图片位置
     * @param dest 输出的位置
     * @param w    缩放后的长
     * @param h    缩放后的宽
     * @throws Exception
     */
    public static void zoomImage(String src, String dest, int w, int h) throws Exception {
        double wr = 0, hr = 0;
        File srcFile = new File(Main.getBaseDir(), src);
        File destFile = new File(Main.getBaseDir(), dest);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        java.awt.Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr = w * 1.0 / bufImg.getWidth();     //获取缩放比例
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
