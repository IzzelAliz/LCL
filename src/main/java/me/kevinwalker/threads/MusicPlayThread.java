package me.kevinwalker.threads;

import me.kevinwalker.utils.GameMusic;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class MusicPlayThread extends Thread {
    private GameMusic music;

    public MusicPlayThread(String file) {
        music = new GameMusic(file);
    }

    @Override
    public void run() {
        music.play();
    }
}
