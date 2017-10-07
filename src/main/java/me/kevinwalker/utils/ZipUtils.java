package me.kevinwalker.utils;

import me.kevinwalker.main.Main;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;

import java.io.File;

/**
 * Created by KevinWalker on 2017/10/7.
 */
public class ZipUtils {
    public static ZipInputStream inputStream;
    ZipFile zFile;

    public ZipUtils(String file){
        try {
            zFile = new ZipFile(new File(Main.getBaseDir(),"LclConfig/skin/"+file+".zip"));
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public ZipInputStream getInputStream(String fileName) {
        try {
            inputStream=zFile.getInputStream(zFile.getFileHeader(fileName));
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
