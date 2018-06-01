package me.kevinwalker.threads;

import javafx.scene.image.Image;
import me.kevinwalker.main.ImageResourc;

public class ImageLoadThread extends Thread {

    public ImageLoadThread() {

    }

    @Override
    public void run() {
        ImageResourc.error = new Image("https://gitee.com/null_179_1072/LCLResource/raw/master/%E5%9B%BE%E6%A0%87%E6%96%87%E4%BB%B6/error.jpg");
    }
}
