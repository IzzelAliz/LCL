package me.kevinwalker.main;

import com.google.gson.Gson;
import me.kevinwalker.utils.io.Files;

import java.util.ArrayList;
import java.util.List;

public class Locale {

    public static Locale instance;

    //界面语言文件
    public String MainPage;
    public String Settings;
    public String ResourceManagement;
    public String ServerData;
    public String Skin;
    public String Resources;

    //设置界面语言文件
    public String HttpProxy;
    public String SocksProxy;

    private static List<String> langs = new ArrayList<String>() {{
        add("zh");
        add("en");
    }};

    public static void load() {
        if (!langs.contains(Config.instance.lang))
            Config.instance.lang = "zh";
        instance = new Gson().fromJson(Files.toString(Locale.class.getResourceAsStream("/lang/" + Config.instance.lang + ".json"), "utf-8"), Locale.class);
    }

}
