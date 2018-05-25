package me.kevinwalker.ui.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Locale;
import me.kevinwalker.utils.io.Updater;

import java.net.Authenticator;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLocale(settingPane.getChildren());

        comboBox.getItems().addAll(
                Locale.instance.HttpProxy,
                Locale.instance.SocksProxy
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
                settingPane.getChildren().add(5, proxySettingBox);
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
            if (comboBox.getValue().equals(Locale.instance.HttpProxy)) proxyPort.setText("80");
            else if (comboBox.getValue().equals(Locale.instance.SocksProxy)) proxyPort.setText("1080");
        });

        connect.setOnMouseClicked((MouseEvent event) -> {
            Properties prop = System.getProperties();
            if (comboBox.getValue().equals(Locale.instance.HttpProxy)) {
                prop.setProperty("http.proxyHost", proxyHost.getText());
                prop.setProperty("http.proxyPort", proxyPort.getText());
            } else if (comboBox.getValue().equals(Locale.instance.SocksProxy)) {
                prop.setProperty("socksProxyHost", proxyHost.getText());
                prop.setProperty("socksProxyPort", proxyPort.getText());
            }
            Authenticator.setDefault(new MyAuthenticator(proxyUser.getText(), proxyPassword.getText()));
        });
    }

    /**
     * 设置语言
     * @param nodeList 控件的List
     */
    private static void setLocale(ObservableList<Node> nodeList){
        nodeList.forEach(node ->{
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
