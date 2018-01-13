package me.kevinwalker.main;

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
import me.kevinwalker.threads.MusicPlayThread;
import me.kevinwalker.ui.Container;
import me.kevinwalker.ui.Skin;
import me.kevinwalker.ui.controller.FrameController;
import me.kevinwalker.ui.controller.InterfaceManager;
import me.kevinwalker.utils.ServerListPing;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    public static MusicPlayThread musicPlayThread;
    private static File bgm;
    public static Stage primaryStage;
    public static Scene scene;

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
        load("MainPage", "主界面", new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "MainPage.png")), true);
        load("Settings", "设置", new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Settings.png")), false);
        load("ResourceManagement", "资源管理", new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ResourceManagement.png")), false);
        load("ResourceManagement", "服务器信息", new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "ResourceManagement.png")), false);
        load("Skin", "启动器皮肤", new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "Skin.png")), false);
        load("Resources", "资源获取", new Image(ZipUtils.getInputStream(new File(Util.getBaseDir(), Config.instance.skin), "loginImg.png")), false);
    }

    private void load(String fxml, String buttonName, Image background, boolean showDefault) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Main.class.getResource("/fxml/" + fxml + ".fxml"));
            panes.put(fxml, parent);
            ImageView imageView = new ImageView(background);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            Button button = new Button(buttonName, imageView);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setPrefSize(200, 50);
            button.setStyle("-fx-border-width: 0;");
            InterfaceManager.addInterface(Container.create(fxml, button));
            if (showDefault) FrameController.instance.pane.getChildren().add(getInstance(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(String fxml, String buttonName, boolean showDefault) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Main.class.getResource("/fxml/" + fxml + ".fxml"));
            panes.put(fxml, parent);
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

    public static void main(String[] args) {
        setupLogger();
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
        Config.load();
        Locale.load();
        Skin.load();
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
}
