package me.kevinwalker.utils.io;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;

import java.io.File;

/**
 * Created by KevinWalker on 2017/10/7.
 */
public class ZipUtils {

    public static ZipInputStream getInputStream(File source, String fileName) {
        try {
            ZipFile zipFile = new ZipFile(source);
            return zipFile.getInputStream(zipFile.getFileHeader(fileName));
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }
}
