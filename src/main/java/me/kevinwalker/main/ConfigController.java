package me.kevinwalker.main;

import com.google.gson.Gson;
import me.kevinwalker.utils.Util;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by KevinWalker on 2017/10/5.
 */
public class ConfigController {
    public static File configFile;
    //public static JSONObject json;

    public ConfigController() {
        File file = new File(Main.getBaseDir(), "LclConfig");
        configFile = new File(Main.getBaseDir(), "LclConfig/config.json");
        File skin = new File(Main.getBaseDir(), "LclConfig/skin");
        File defaultSkin = new File(Main.getBaseDir(), "LclConfig/skin/default.zip");

        if (!file.exists()) {
            file.mkdirs();
        }
        if (!skin.exists()) {
            skin.mkdirs();
        }
        if (!configFile.exists()) {
            Util.saveResource("config.json", new File(Main.getBaseDir(), "LclConfig/config.json"));
        }
        if (!defaultSkin.exists()) {
            Util.saveResource("default.zip", new File(Main.getBaseDir(), "LclConfig/skin/default.zip"));
        }
        Config.instance = new Gson().fromJson(Main.Files.toString(new File(Main.getBaseDir(), "LclConfig/config.json"), "utf-8"), Config.class);
        //json = new JSONObject(new JSONTokener(Main.Files.toString(new File(Main.getBaseDir(), "LclConfig/config.json"), "utf-8")));
    }

    public static void saveJson() {
        try (OutputStream out = new FileOutputStream(configFile)) {
            out.write(new Gson().toJson(Config.instance).getBytes(Charset.forName("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
