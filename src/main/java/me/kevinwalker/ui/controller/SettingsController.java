package me.kevinwalker.ui.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.kevinwalker.main.Apis;
import me.kevinwalker.main.Config;
import me.kevinwalker.utils.io.Updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsController implements Initializable {
    private static ExecutorService service = Executors.newFixedThreadPool(1);

    public Button enableProxy;
    public VBox proxySettingBox, settingPane;
    public TextField proxyHost;
    public TextField proxyPort;
    public TextField proxyUser;
    public TextField proxyPassword;
    public Button checkUpdate, connect;
    public Text versionInfo;
    public ComboBox comboBox;

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
        comboBox.getItems().addAll(
                "Http代理",
                "Socks代理"
        );

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
        versionInfo.setText("当前版本 " + Updater.currentVersion() + "/" + Updater.currentCommit());
        checkUpdate.setOnMouseClicked((MouseEvent event) -> service.execute(() -> {
            String v = Updater.checkUpdate();
            Platform.runLater(() -> versionInfo.setText("最新版本 " + v));
        }));

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (comboBox.getValue().equals("Http代理")) proxyPort.setText("80");
            else if (comboBox.getValue().equals("Socks代理")) proxyPort.setText("1080");
        });

        connect.setOnMouseClicked((MouseEvent event) -> {
            Properties prop = System.getProperties();
            if (comboBox.getValue().equals("Http代理")) {
                prop.setProperty("http.proxyHost", proxyHost.getText());
                prop.setProperty("http.proxyPort", proxyPort.getText());
            } else if (comboBox.getValue().equals("Socks代理")) {
                prop.setProperty("socksProxyHost", proxyHost.getText());
                prop.setProperty("socksProxyPort", proxyPort.getText());
            }
            Authenticator.setDefault(new MyAuthenticator(proxyUser.getText(), proxyPassword.getText()));
        });
    }

    static class MyAuthenticator extends Authenticator {
        private String user = "";
        private String password = "";

        public MyAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password.toCharArray());
        }
    }
}
