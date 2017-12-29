package me.kevinwalker.utils;


import java.awt.*;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class ColorTranslated {

    private static String toHexFromColor(Color color){
        String r,g,b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() ==1 ? "0" +g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("0xFF");
        su.append(r);
        su.append(g);
        su.append(b);
        //0xFF0000FF
        return su.toString();
    }
    /**
     * 字符串转换成Color对象
     * @param colorStr 16进制颜色字符串
     * @return Color对象
     * */
    public static Color toColorFromString(String colorStr){
        colorStr = colorStr.substring(1);
        Color color =  new Color(Integer.parseInt(colorStr, 16)) ;
        return color;
    }
    public static String toHex(int num){
        char[] chs = new char[8];//定义容器，存储的是字符，长度为8.一个整数最多8个16进制数
        int index = chs.length-1;
        for(int i = 0;i<8;i++) {
            int temp = num & 15;

            if(temp > 9){
                chs[index] = ((char)(temp-10+'A'));
            }else {
                chs[index] = ((char)(temp+'0'));
            }

            index--;
            num = num >>> 4;
        }
        return toString(chs);
    }
    //将数组转为字符串
    public static String toString(char[] arr){
        String temp = "";
        for(int i = 0;i<arr.length;i++){
            temp = temp + arr[i];
        }
        return temp;
    }
}
