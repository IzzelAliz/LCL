package me.kevinwalker.ui;

import javafx.scene.image.Image;
import me.kevinwalker.main.Config;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.ZipUtils;

import java.io.File;

public class Skin {

    public static void load() {
        File folder = new File(Util.getBaseDir(), "/LcLConfig/skin");
        if (!folder.exists()) folder.mkdir();
        if (folder.listFiles() == null || folder.listFiles().length == 0) {
            Util.saveResource("default.zip", new File(folder, "default.zip"));
        }
        background = new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "background.png"));
    }

    private static Image background;

    public static Image getBackground() {
        return background;
    }
}
