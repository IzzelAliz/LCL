package me.kevinwalker.ui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.kevinwalker.main.GuiBase;
import me.kevinwalker.main.Main;
import me.kevinwalker.ui.controller.PopupController;

public class Popup {
    public void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.getIcons().add(new Image(Main.class.getResourceAsStream("/css/images/LCL.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setMinWidth(300);
        window.setMinHeight(150);
        GuiBase guiBase = null;
        try {
            guiBase = new GuiBase("Popup",window,300,150);
        } catch (Exception e) {
            e.printStackTrace();
        }
        guiBase.getScene().setFill(Color.rgb(21,21,21,1));
        PopupController.instance.message.setText(message);
        PopupController.instance.cancel.setOnAction(e -> window.close());
        window.setScene(guiBase.getScene());
        window.showAndWait();
    }
}
