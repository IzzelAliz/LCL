package me.kevinwalker.utils;

import javafx.scene.paint.Color;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class ColorTranslated {
    /**
     * Color对象转换成字符串
     * @param color Color对象
     * @return 16进制颜色字符串
     * */
    public static String toHexFromColor(Color color){
        String r,g,b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString((int)color.getRed());
        g = Integer.toHexString((int)color.getGreen());
        b = Integer.toHexString((int)color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() ==1 ? "0" +g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
//        su.append("0xFF");
        su.append(r);
        su.append(g);
        su.append(b);
        return su.toString();
    }

    public static int CC(int c){
        int cc = 255 - c;
        if(cc>64 && cc<128)
            cc-=64;
        else if(cc>=128 && cc<192)
            cc+=64;
        return cc;
    }

    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
                + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(
                Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 反转颜色
     * @param color 输入的颜色
     * @return 返回Color
     */
    public static Color Color2Contrary2(Color color) {
        return Color.rgb(CC((int) color.getRed()), CC((int)color.getGreen()), CC((int)color.getBlue()));
    }
}
