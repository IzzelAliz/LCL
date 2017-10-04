package me.kevinwalker.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.kevinwalker.guis.GuiBase;

public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage=primaryStage;

		// File file = new File("LclConfig");
		// if(!file.exists()){
		// file.mkdirs();
		// }
		GuiBase mainGui = new GuiBase("LoginCraftLaunch",this.stage,800,500);
		mainGui.getStage().setTitle("LoginCraftLaunch-0.0.1Demo");
		mainGui.getStage().initStyle(StageStyle.TRANSPARENT);
		mainGui.getStage().setAlwaysOnTop(true);
		mainGui.getStage().setResizable(false);
		mainGui.getScene().setFill(null);
		mainGui.getStage().setOnCloseRequest((e) -> {
			System.exit(0);
		});
		mainGui.show();
	}

	public static void main(String[] args) throws Exception {
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
}
