package me.kevinwalker.ui;

import javafx.scene.image.Image;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Locale;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.ZipUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class Skin {

    public static void load() {
        try {
            File folder = new File(Util.getBaseDir(), "/LcLConfig/skin");
            if (!folder.exists()) folder.mkdir();
            if (folder.listFiles() == null || Objects.requireNonNull(folder.listFiles()).length == 0) {
                Util.saveResource("default.zip", new File(folder, "default.zip"));
            }
            mainBackground = new Image(Objects.requireNonNull(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "MainBackground.png")));
            background = new Image(Objects.requireNonNull(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "background.png")));
        }catch (Exception e){
            new Popup().display(Locale.instance.Error, e.toString());
        }
    }

    private static Image mainBackground;
    private static Image background;

    public static Image getMainBackground() {
        return mainBackground;
    }

    public static Image getBackground() {
        return background;
    }
}
