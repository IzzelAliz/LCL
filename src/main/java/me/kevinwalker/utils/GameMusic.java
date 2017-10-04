package me.kevinwalker.utils;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by KevinWalker on 2017/10/4.
 */
public class GameMusic {
    private String filename = null;
    private Player player = null;

    public GameMusic(String filename) {
        this.filename = filename;
    }

    public void play() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(buffer);
            player.play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close() {
        this.player.close();
    }
}