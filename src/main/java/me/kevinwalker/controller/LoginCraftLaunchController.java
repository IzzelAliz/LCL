package me.kevinwalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.Main;
import me.kevinwalker.threads.MusicPlayThread;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/9/16.
 */
public class LoginCraftLaunchController implements Initializable {
	private String BackGroundRGB = "derive(#7A7A7A,85%)";
	public static MusicPlayThread musicPlayer = new MusicPlayThread();

	@FXML
	private Button closebtn;

	@FXML
	private SVGPath handsvg;

	@FXML
	private Pane MainGui;

	@FXML
	private Button launch, setting, bulletin, login, resourceManagement, custom, getResources,serverInformation,author,update;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//        File file = new File("LclConfig/background.png");
//        if (file.exists()) {
//            this.BackGroundRGB = GetMainColor.getImagePixel(file);
//        }
		onGuiOpen(launch);
		onGuiOpen(setting);
		onGuiOpen(bulletin);
		onGuiOpen(login);
		onGuiOpen(resourceManagement);
		onGuiOpen(custom);
		onGuiOpen(getResources);
		onGuiOpen(serverInformation);
		onGuiOpen(author);
		onGuiOpen(update);
		musicPlayer.start();
		handsvg.setStyle("-fx-fill:" + this.BackGroundRGB);

		launch.setOnAction(oa -> {
			try {
				musicPlayer.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		setting.setOnAction(oa -> {
			try {
				new GuiBase("Setting", Main.stage,800,500).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void onCloseButtonAction(ActionEvent event) {
		System.exit(0);
	}

	public static void onGuiOpen(Button button) {
		int red;
		int green;
		int blue;
		Random random = new Random();
		red = random.nextInt(120)+50;
		green = random.nextInt(130)+50;
		blue = random.nextInt(120)+50;
		button.setStyle("-fx-background-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-width: 1;");
	}

}
