package me.kevinwalker.ui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import me.kevinwalker.main.Apis;
import me.kevinwalker.main.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    public Button enableProxy;
    public VBox proxySettingBox, settingPane;
    public TextField proxyHost;
    public TextField proxyPort;
    public TextField proxyUser;
    public TextField proxyPassword;

    public static void main(String[] args) {
        try {
            URL url = new URL(Apis.UPDATE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(reader.readLine());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(element));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Config.instance.enableProxy) {
            enableProxy.setStyle("-fx-opacity: 0.4;" +
                    " -fx-background-color: #1d1d1d;" +
                    " -fx-text-fill: #e2e2e2;" +
                    " -fx-border-color: #778899;" +
                    " -fx-border-width: 2.0;");
        }
        proxyHost.setText(Config.instance.proxyHost);
        proxyPort.setText(Config.instance.proxyPort);
        proxyUser.setText(Config.instance.proxyUser);
        proxyPassword.setText(Config.instance.proxyPassword);
        if (!Config.instance.enableProxy)
            settingPane.getChildren().remove(proxySettingBox);
        enableProxy.setOnMouseClicked(event -> {
            if (!Config.instance.enableProxy) {
                settingPane.getChildren().add(1, proxySettingBox);
                enableProxy.setStyle("");
                Config.instance.enableProxy = true;
            } else {
                settingPane.getChildren().remove(proxySettingBox);
                enableProxy.setStyle("-fx-opacity: 0.4;" +
                        " -fx-background-color: #1d1d1d;" +
                        " -fx-text-fill: #e2e2e2;" +
                        " -fx-border-color: #778899;" +
                        " -fx-border-width: 2.0;");
                Config.instance.enableProxy = false;
            }
        });
    }

}
