package me.kevinwalker.utils;

import me.kevinwalker.main.Main;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

public class Util {

    /**
     * 江jar内文件加载为 byte[] 格式
     *
     * @param path 不带第一个斜杠的路径
     * @return byte[]
     */

    public static byte[] loadResource(String path) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            InputStream in = Util.class.getClassLoader().getResourceAsStream(path);
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 江内部文件挪到外部
     *
     * @param path   内部路径
     * @param target 生成路径
     */
    public static void saveResource(String path, File target) {
        try {
            InputStream in = Util.class.getClassLoader().getResourceAsStream(path);
            byte[] buffer = new byte[1024];
            int length = -1;
            OutputStream out = new FileOutputStream(target);
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

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
