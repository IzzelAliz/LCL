package me.kevinwalker.utils.io;

import java.io.*;

public class Files {

    public static void write(byte[] buf, File des) {
        try {
            des.delete();
            des.createNewFile();
            FileOutputStream stream = new FileOutputStream(des);
            stream.write(buf);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String content, File des, String charset) {
        try {
            write(content.getBytes(charset), des);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String toString(InputStream in, String charset) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int length = -1;
        byte[] buffer = new byte[1024];
        try {
            while ((length = in.read(buffer)) != -1)
                stream.write(buffer, 0, length);
            return new String(stream.toByteArray(), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(File file, String charset) {
        try {
            if (file.exists())
                return toString(new FileInputStream(file), charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}