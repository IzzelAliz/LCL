package me.kevinwalker.main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.threads.MusicPlayThread;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.ZipUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;

public class Main extends Application {
    public static MusicPlayThread musicPlayThread;
    private static File bgm;
    public static Stage primaryStage;
    public static GuiBase mainGui;
    public static GuiBase author;
    public static GuiBase setting;
    public static GuiBase getResources;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        mainGui = new GuiBase("LoginCraftLaunch", primaryStage, 800, 530);
        mainGui.getStage().setTitle("LoginCraftLaunch-0.0.1Demo");
        mainGui.getStage().getIcons().add(new Image(Main.class.getResourceAsStream("/css/images/LCL.png")));
        mainGui.getStage().initStyle(StageStyle.TRANSPARENT);
        mainGui.getStage().setResizable(false);
        mainGui.getScene().setFill(Color.WHITE);
        mainGui.getStage().setOnCloseRequest((e) -> {
            System.exit(0);
        });
        GuiMain(primaryStage);
        //播放音乐
        bgm = new File(Main.getBaseDir(), "LclConfig/" + ConfigController.json.getString("bgm"));
        if (bgm.exists()) {
            musicPlayThread = new MusicPlayThread(bgm.getPath());
        } else {
            Util.saveResource("css/music/bgm.mp3", new File(Main.getBaseDir(), "LclConfig/bgm.mp3"));
            File musicFile = new File(Main.getBaseDir(), "LclConfig/bgm.mp3");
            musicPlayThread = new MusicPlayThread(musicFile.getPath());
        }
        musicPlayThread.start();
        mainGui.show();
    }

    void GuiMain(Stage stage) {
        Main.author = new GuiBase("Author", stage, 800, 530);
        Main.setting = new GuiBase("Setting", stage, 800, 530);
        Main.getResources = new GuiBase("GetResources", stage, 800, 530);
    }

    public static void main(String[] args) {
//        setupLogger();
        new ConfigController();
        launch(args);
        // ServerListPing slp = new ServerListPing();
        // InetSocketAddress sadd0 = new InetSocketAddress("dx.mc11.icraft.cc", 37190);
        // InetSocketAddress sadd1 = new InetSocketAddress("218.93.208.142", 10648);
        // InetSocketAddress sadd2 = new InetSocketAddress("four.mengcraft.com", 13433);
        // InetSocketAddress sadd3 = new InetSocketAddress("r2.suteidc.com", 26339);
        // InetSocketAddress sadd = new InetSocketAddress("103.37.45.108", 7160);
        //
        // slp.setAddress(sadd);
        // ServerListPing.StatusResponse sr = slp.fetchData();
        // System.out.println("最大在线" + sr.getPlayers().getMax());
        // System.out.println("在线" + sr.getPlayers().getOnline());
        // if(sr.getPlayers().getSample()!= null) {
        // for (int i = 0; i < sr.getPlayers().getSample().size(); i++) {
        // System.out.println("玩家"+i+":"+sr.getPlayers().getSample().get(i).getName());
        // }
        // }
    }

    public static void setupLogger() {
        try {
            if (!new File(getBaseDir(), "/LclConfig").exists())
                new File(getBaseDir(), "/LclConfig").mkdir();
            if (!new File(getBaseDir(), "LclConfig/log.txt").exists())
                new File(getBaseDir(), "LclConfig/log.txt").createNewFile();
            System.setOut(new PrintStream(new File(getBaseDir(), "LclConfig/log.txt")));
            System.setErr(new PrintStream(new File(getBaseDir(), "LclConfig/log.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getBaseDir() {
        try {
            File file = new File(URLDecoder
                    .decode(Main.class.getProtectionDomain().getCodeSource().getLocation().toString(), "utf-8")
                    .substring(6));
            return file.getParentFile();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static class Files {
        public static String toString(InputStream in, String charset) {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            int length = -1;
            byte[] buffer = new byte[1024];
            try {
                while ((length = in.read(buffer)) != -1)
                    bb.put(buffer);
                return new String(bb.array(), charset);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static String toString(File file, String charset) {
            try {
                if (!file.exists())
                    return null;
                RandomAccessFile rf = new RandomAccessFile(file, "r");
                rf.seek(0);
                long length = rf.length();
                byte[] content = new byte[(int) length];
                rf.readFully(content);
                rf.close();
                return new String(content, charset);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
