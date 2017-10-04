package me.kevinwalker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
<<<<<<< HEAD:src/main/java/me/kevinwalker/controller/LoginCraftLaunchController.java
public class LoginCraftLaunchController implements Initializable {
    private String BackGroundRGB = "#7A7A7A";
    public static MusicPlayThread musicPlayer = new MusicPlayThread();
=======
public class LoginCraftLaunchMainUI implements Initializable {
	private String BackGroundRGB = "#7A7A7A";
	public static MusicPlayThread musicPlayer = new MusicPlayThread();
>>>>>>> 0c144c7f5def5bb5ebd5319c0a387ce43e289e05:src/main/java/me/kevinwalker/guis/LoginCraftLaunchMainUI.java

	@FXML
	private Button closebtn;

	@FXML
	private AnchorPane TitlePane;

	@FXML
	private SVGPath handsvg;

<<<<<<< HEAD:src/main/java/me/kevinwalker/controller/LoginCraftLaunchController.java
    @FXML
    private Button launch, setting, bulletin, login, resourceManagement, custom, getResources, serverInformation, author, update;

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
                new GuiBase("Setting", Main.stage, 800, 500).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
=======
	@FXML
	private Pane MainGui;

	@FXML
	private Button launch, setting, bulletin, login, resourceManagement, custom, getResources, serverInformation,
			author, update;
>>>>>>> 0c144c7f5def5bb5ebd5319c0a387ce43e289e05:src/main/java/me/kevinwalker/guis/LoginCraftLaunchMainUI.java

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// File file = new File("LclConfig/background.png");
		// if (file.exists()) {
		// this.BackGroundRGB = GetMainColor.getImagePixel(file);
		// }
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
		closebtn.getStyleClass().add("closebutton");
		launch.setOnAction(oa -> {
			try {
				musicPlayer.interrupt();;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

<<<<<<< HEAD:src/main/java/me/kevinwalker/controller/LoginCraftLaunchController.java
    public static void onGuiOpen(Button button) {
        int red;
        int green;
        int blue;
        Random random = new Random();
        red = random.nextInt(120) + 50;
        green = random.nextInt(130) + 50;
        blue = random.nextInt(120) + 50;
        button.setStyle("-fx-background-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-width: 1;");
    }
=======
	public void onCloseButtonAction(ActionEvent event) {
		System.exit(0);
	}

	public static void onGuiOpen(Button button) {
		int red;
		int green;
		int blue;
		Random random = new Random();
		red = random.nextInt(120) + 50;
		green = random.nextInt(130) + 50;
		blue = random.nextInt(120) + 50;
		button.setStyle("-fx-background-color: rgba(" + red + "," + green + "," + blue + ",0.9);-fx-border-color: rgba("
				+ red + "," + green + "," + blue + ",0.9);-fx-border-width: 1;");
	}
>>>>>>> 0c144c7f5def5bb5ebd5319c0a387ce43e289e05:src/main/java/me/kevinwalker/guis/LoginCraftLaunchMainUI.java

}
