package me.kevinwalker.threads;

import me.kevinwalker.utils.GameMusic;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class MusicPlayThread extends Thread {
    public static GameMusic music = new GameMusic("LclConfig/bgm.mp3");

    public MusicPlayThread() {
    }

    @Override
    public void run() {
        music.play();
    }
}
