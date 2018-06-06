package me.kevinwalker.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.kevinwalker.threads.ImageLoadThread;
import me.kevinwalker.threads.MusicPlayThread;
import me.kevinwalker.ui.Container;
import me.kevinwalker.ui.InterfaceManager;
import me.kevinwalker.ui.Popup;
import me.kevinwalker.ui.Skin;
import me.kevinwalker.ui.controller.FrameController;
import me.kevinwalker.ui.controller.ResourceController;
import me.kevinwalker.utils.ColorTranslated;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.ZipUtils;
import net.lingala.zip4j.io.ZipInputStream;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    public static MusicPlayThread musicPlayThread;
    private static File bgm;
    public static Stage primaryStage;
    public static Scene scene;
    public static java.awt.Color awtColor = null;

    private static final Map<String, Node> panes = new HashMap<>();

    public static Node getInstance(String name) {
        return panes.get(name);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        GuiBase mainGui = new GuiBase("Frame", primaryStage);
        mainGui.getScene().setFill(Color.TRANSPARENT);
        mainGui.getStage().setTitle("LoginCraftLaunch-0.0.1Demo");
        mainGui.getStage().getIcons().add(new Image(Main.class.getResourceAsStream("/css/images/LCL.png")));
        mainGui.getStage().initStyle(StageStyle.TRANSPARENT);
        mainGui.getStage().setResizable(true);
        mainGui.getStage().setOnCloseRequest((e) -> {
            Config.save();
            System.exit(0);
        });
        mainGui.getStage().setScene(mainGui.getScene());
        mainGui.getStage().show();

        try (ZipInputStream skinInputStream = ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "skin.json")) {
            Reader reader = new InputStreamReader(skinInputStream, "UTF-8");
            Gson json = new GsonBuilder().create();
            FrameController.SkinData user = json.fromJson(reader, FrameController.SkinData.class);
            Main.awtColor = ColorTranslated.toColorFromString(user.colorTitle);
            load("MainPage", Locale.instance.MainPage, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "MainPage.png")), awtColor, true);
            load("Settings", Locale.instance.Settings, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Settings.png")), awtColor, false);
            load("ResourceManagement", Locale.instance.ResourceManagement, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ResourceManagement.png")), awtColor, false);
            load("ResourceManagement", Locale.instance.ServerData, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ServerInformation.png")), awtColor, false);
            load("Skin", Locale.instance.Skin, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Skin.png")), awtColor, false);
            load("Resources", Locale.instance.Resources, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "loginImg.png")), awtColor, false);
            ResourceController.instance.fetch();
        } catch (Exception e) {
            Config.instance.skin = "/LcLConfig/skin/default.zip";
            awtColor = new java.awt.Color(255, 255, 255);
            new Popup().display(Locale.instance.Error, e.toString());
            load("MainPage", Locale.instance.MainPage, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "MainPage.png")), awtColor, true);
            load("Settings", Locale.instance.Settings, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Settings.png")), awtColor, false);
            load("ResourceManagement", Locale.instance.ResourceManagement, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ResourceManagement.png")), awtColor, false);
            load("ResourceManagement", Locale.instance.ServerData, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ServerInformation.png")), awtColor, false);
            load("Skin", Locale.instance.Skin, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Skin.png")), awtColor, false);
            load("Resources", Locale.instance.Resources, new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "loginImg.png")), awtColor, false);
            ResourceController.instance.fetch();
        }

    }

    public static void load(String fxml, String buttonName, Image background, java.awt.Color color, boolean showDefault) {
        Parent parent;
        try {
            parent = FXMLLoader.load(Main.class.getResource("/fxml/" + fxml + ".fxml"));
            panes.put(fxml, parent);
            FrameController.instance.apane.setPrefHeight(panes.size() * 50 + 100);
            ImageView imageView = new ImageView(background);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            Button button = new Button(buttonName, imageView);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setPrefSize(200, 50);
            button.setStyle("-fx-border-style: solid;" +
                    "-fx-border-width: 0px 0px 0px 10px;" +
                    "-fx-border-color: rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0);");

            if (showDefault) {
                Main.awtColor = color;
                button.setStyle("-fx-border-style: solid;" +
                        "-fx-border-width: 0px 0px 0px 10px;" +
                        "-fx-border-color: rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(0, 0, 0, 0) rgba(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ");");
            }
            InterfaceManager.addInterface(Container.create(fxml, button));

            if (showDefault)
                FrameController.instance.pane.getChildren().add(getInstance(fxml));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(String fxml, String buttonName, boolean showDefault) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Main.class.getResource("/fxml/" + fxml + ".fxml"));
            panes.put(fxml, parent);
            FrameController.instance.apane.setPrefHeight(panes.size() * 50 + 100);
            Button button = new Button(buttonName);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setPrefSize(200, 50);
            button.setStyle("-fx-border-width: 0;");
            InterfaceManager.addInterface(Container.create(fxml, button));
            if (showDefault) FrameController.instance.pane.getChildren().add(getInstance(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
//        setupLogger();
//        ServerListPing slp = new ServerListPing();
//        InetSocketAddress sadd0 = new InetSocketAddress("dx.mc11.icraft.cc", 37190);
//        InetSocketAddress sadd1 = new InetSocketAddress("218.93.208.142", 10648);
//        InetSocketAddress sadd2 = new InetSocketAddress("four.mengcraft.com", 13433);
//        InetSocketAddress sadd3 = new InetSocketAddress("r2.suteidc.com", 26339);
//        InetSocketAddress sadd = new InetSocketAddress("play.mcartoria.com", 25565);
//
//        slp.setAddress(sadd);
//        ServerListPing.StatusResponse sr = null;
//        try {
//            sr = slp.fetchData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("最大在线" + sr.getPlayers().getMax());
//        System.out.println("在线" + sr.getPlayers().getOnline());
//        if (sr.getPlayers().getSample() != null) {
//            for (int i = 0; i < sr.getPlayers().getSample().size(); i++) {
//                System.out.println("玩家" + i + ":" + sr.getPlayers().getSample().get(i).getName());
//            }
//        }

        if (isOnLine())
            new ImageLoadThread().start();

        Config.load();
        Locale.load();
        Skin.load();
//        setupLogger();
        launch(args);
    }


    private static void setupLogger() {
        try {
            if (!new File(Util.getBaseDir(), "/LclConfig").exists())
                new File(Util.getBaseDir(), "/LclConfig").mkdir();
            if (new File(Util.getBaseDir(), "LclConfig/log.txt").exists())
                new File(Util.getBaseDir(), "LclConfig/log.txt").delete();
            new File(Util.getBaseDir(), "LclConfig/log.txt").createNewFile();
            System.setOut(new PrintStream(new File(Util.getBaseDir(), "LclConfig/log.txt")));
            System.setErr(new PrintStream(new File(Util.getBaseDir(), "LclConfig/log.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnLine() {
        URL url = null;
        try {
            url = new URL("https://www.baidu.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        int timeOut = 3000;
        boolean status = false;
        try {
            status = InetAddress.getByName(url.getHost()).isReachable(timeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}
