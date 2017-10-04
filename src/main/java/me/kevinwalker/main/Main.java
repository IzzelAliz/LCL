package me.kevinwalker.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.kevinwalker.guis.GuiBase;

public class Main extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		// File file = new File("LclConfig");
		// if(!file.exists()){
		// file.mkdirs();
		// }
		GuiBase mainGui = new GuiBase("LoginCraftLaunch", this.stage, 800, 500);
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
		setupLogger();
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
			if (!new File(getBaseDir(), "log.txt").exists())
				new File(getBaseDir(), "log.txt").createNewFile();
			System.setOut(new PrintStream(new File(getBaseDir(), "log.txt")));
			System.setErr(new PrintStream(new File(getBaseDir(), "log.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
