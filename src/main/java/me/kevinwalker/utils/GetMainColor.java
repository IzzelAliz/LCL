package me.kevinwalker.utils;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KevinWalker on 2017/10/3.
 */
public class GetMainColor {

    public static Color getImagePixel(File file) {
        try {
            return getImagePixel(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Color.WHITE;
    }


    public static Color getImagePixel(InputStream file) {
        int R = 0;
        int G = 0;
        int B = 0;
        List<String> list = new ArrayList<String>();
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int sum = width * height;
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j);
                R = (pixel & 0xff0000) >> 16;
                G = (pixel & 0xff00) >> 8;
                B = (pixel & 0xff);
                list.add(R + "-" + G + "-" + B);
            }
        }
        return getMaxCount(list);
    }

    public static Color getMaxCount(List<String> s) {
        List<String> list = s;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String c : list) {
            Integer l = map.get(c);
            if (l == null)
                l = 1;
            else
                l++;
            map.put(c, l);
        }
        String max = null;
        long num = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer temp = entry.getValue();
            if (max == null || temp > num) {
                max = key;
                num = temp;
            }
        }
        String str[] = max.split("\\-");
        if (str.length == 3) {
            return Color.rgb(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
                    Integer.parseInt(str[2]));
        }
        return Color.WHITE;
    }
}
