package me.kevinwalker.main;

import com.google.gson.Gson;
import me.kevinwalker.utils.io.Files;

import java.util.ArrayList;
import java.util.List;

public class Locale {

    String skinName;

    private static List<String> langs = new ArrayList<String>() {{
        add("zh");
        add("en");
    }};

    public static void load() {
        if (!langs.contains(Config.instance.lang))
            Config.instance.lang = "zh";
        Locale locale = new Gson().fromJson(Files.toString(Locale.class.getResourceAsStream("/lang/" +
                Config.instance.lang + ".json"), "utf-8"), Locale.class);
    }

}
