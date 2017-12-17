package me.kevinwalker.utils;

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
}
