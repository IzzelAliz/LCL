package me.kevinwalker.main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.utils.Json;
import me.kevinwalker.utils.Util;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URLDecoder;

public class Main extends Application {
    public static JSONObject json;
    public static GuiBase mainGui;

    public static GuiBase author;
    public static GuiBase setting;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainGui = new GuiBase("LoginCraftLaunch", primaryStage, 800, 530);
        mainGui.getStage().setTitle("LoginCraftLaunch-0.0.1Demo");
        mainGui.getStage().initStyle(StageStyle.TRANSPARENT);
        mainGui.getStage().setAlwaysOnTop(true);
        mainGui.getStage().setResizable(false);
        mainGui.getScene().setFill(null);

        mainGui.getStage().setOnCloseRequest((e) -> {
            System.exit(0);
        });
        GuiMain(primaryStage);
        mainGui.show();
    }

    void GuiMain(Stage stage) {
        Main.author = new GuiBase("Author", stage, 800, 530);
        Main.setting = new GuiBase("Setting", stage, 800, 530);
    }

    public static void main(String[] args) throws Exception {
        setupLogger();
        File file = new File(getBaseDir(), "LclConfig");
        File config = new File(getBaseDir(), "LclConfig/config.json");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!config.exists()) {
            Util.saveResource("config.json", new File(getBaseDir(), "LclConfig/config.json"));
        }
        json = new JSONObject(new JSONTokener(Json.Files.toString(new File(getBaseDir(), "LclConfig/config.json"), "utf-8")));
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
}
