package me.kevinwalker.main;

import com.google.gson.Gson;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.Files;

import java.io.File;
import java.nio.charset.Charset;

public class Config {

    public static Config instance;

    public String background, lang = java.util.Locale.getDefault().getLanguage();

    public String name;

    public String skin = "/LcLConfig/skin/default.zip";

    public String bgm;

    public String authType;

    public boolean enableProxy = false;
    public String proxyHost, proxyPort, proxyUser, proxyPassword;

    public static void load() {
        File fo = new File(Util.getBaseDir(), "/LcLConfig");
        if (!fo.exists()) fo.mkdir();
        File file = new File(Util.getBaseDir(), "/LcLConfig/config.json");
        instance = new Gson().fromJson(Files.toString(file, "utf-8"), Config.class);
    }

    public static void save() {
        Files.write(new Gson().toJson(instance).getBytes(Charset.forName("utf-8")),
                new File(Util.getBaseDir(), "/LcLConfig/config.json"));
    }

}
