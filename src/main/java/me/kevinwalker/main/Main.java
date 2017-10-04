package me.kevinwalker.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        File file = new File("LclConfig");
//        if(!file.exists()){
//            file.mkdirs();
//        }
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginCraftLaunch.fxml"));
        root.setOnMousePressed((MouseEvent event) -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            event.consume();
            primaryStage.setX(event.getScreenX() - xOffset);

            if (event.getScreenY() - yOffset < 0) {
                primaryStage.setY(0);
            } else {
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        primaryStage.setTitle("LoginCraftLaunch-0.0.1Demo");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 800, 500);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
//        ServerListPing slp = new ServerListPing();
//        InetSocketAddress sadd0 = new InetSocketAddress("dx.mc11.icraft.cc", 37190);
//        InetSocketAddress sadd1 = new InetSocketAddress("218.93.208.142", 10648);
//        InetSocketAddress sadd2 = new InetSocketAddress("four.mengcraft.com", 13433);
//        InetSocketAddress sadd3 = new InetSocketAddress("r2.suteidc.com", 26339);
//        InetSocketAddress sadd = new InetSocketAddress("103.37.45.108", 7160);
//
//        slp.setAddress(sadd);
//        ServerListPing.StatusResponse sr = slp.fetchData();
//        System.out.println("最大在线" + sr.getPlayers().getMax());
//        System.out.println("在线" + sr.getPlayers().getOnline());
//        if(sr.getPlayers().getSample()!= null) {
//            for (int i = 0; i < sr.getPlayers().getSample().size(); i++) {
//                System.out.println("玩家"+i+":"+sr.getPlayers().getSample().get(i).getName());
//            }
//        }
    }
}
