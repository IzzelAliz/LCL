package me.kevinwalker.main;

import me.kevinwalker.utils.Util;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

/**
 * Created by KevinWalker on 2017/10/5.
 */
public class ConfigController {
    public static File configFile;
    public static JSONObject json;
    public static JSONObject skinJson;

    public ConfigController(){
        File file = new File(Main.getBaseDir(), "LclConfig");
        configFile = new File(Main.getBaseDir(), "LclConfig/config.json");
        File skin = new File(Main.getBaseDir(), "LclConfig/skin");
        File defaultSkin = new File(Main.getBaseDir(), "LclConfig/skin");

        if (!file.exists()) {
            file.mkdirs();
        }
        if (!skin.exists()) {
            file.mkdirs();
        }
        if (!configFile.exists()) {
            Util.saveResource("config.json", new File(Main.getBaseDir(), "LclConfig/config.json"));
        }
        if (!defaultSkin.exists()) {
            Util.saveResource("default.zip", new File(Main.getBaseDir(), "LclConfig/skin/default.zip"));
        }
        json = new JSONObject(new JSONTokener(Main.Files.toString(new File(Main.getBaseDir(), "LclConfig/config.json"), "utf-8")));
    }

    public static void saveJson() {
        OutputStream out = null;
        try {
            out = new FileOutputStream(configFile);
            out.write(json.toString().getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
