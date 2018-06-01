package me.kevinwalker.ui.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Locale;
import me.kevinwalker.main.Main;
import me.kevinwalker.ui.Popup;
import me.kevinwalker.utils.io.Updater;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsController implements Initializable {
    public static SettingsController instance;
    private static ExecutorService service = Executors.newFixedThreadPool(1);

    public ScrollPane settingPane;

    public Text Setting;
    public Text GameSettings, LaunchSetting, NetworkSetting;
    public Text PlayerName, JavaPath, Language, ProxyMode, ProxyHost, ProxyPort, ProxyUserName, ProxyPassword;

    public TextField name;
    public TextField javaPath;
    public TextField proxyHost, proxyPort, proxyUser, proxyPassword;

    public ComboBox comboBoxLanguage, comboBoxProxy;

    public Text VersionInfo;

    public Button checkUpdate, connect, LanguageButton;
    public Button chooeFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        setLocale();
        javaPath.setText(Config.instance.javaPath);
        name.setText(Config.instance.name);

        comboBoxLanguage.getItems().addAll("中文简体", "English");

        comboBoxProxy.getItems().addAll(
                Locale.instance.HttpProxy,
                Locale.instance.SocksProxy
        );

        if (Config.instance.lang.equals("zh")) {
            comboBoxLanguage.setValue("中文简体");
        } else {
            comboBoxLanguage.setValue("English");
        }

        if (Config.instance.proxyMode == null) comboBoxProxy.setValue(null);
        else if (Config.instance.proxyMode.equals("http"))
            comboBoxProxy.setValue(Locale.instance.HttpProxy);
        else comboBoxProxy.setValue(Locale.instance.SocksProxy);
        proxyHost.setText(Config.instance.proxyHost);
        proxyPort.setText(Config.instance.proxyPort);
        proxyUser.setText(Config.instance.proxyUser);

        chooeFile.setOnAction(oa -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(Locale.instance.JavaPath);
            fileChooser.setInitialFileName("javaw");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("javaw", "javaw.exe")
            );
            File file = fileChooser.showOpenDialog(Main.primaryStage);
            javaPath.setText(file.getPath());
            Config.instance.javaPath = file.getPath();
            Config.save();
        });

//        VersionInfo.setText("当前版本 " + Updater.currentVersion() + "/" + Updater.currentCommit());
//        checkUpdate.setOnMouseClicked((MouseEvent event) -> service.execute(() -> {
//            String v = Updater.checkUpdate();
//            Platform.runLater(() -> VersionInfo.setText("最新版本 " + v));
//        }));

        comboBoxProxy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (comboBoxProxy.getValue().equals(Locale.instance.HttpProxy)) proxyPort.setText("80");
            else if (comboBoxProxy.getValue().equals(Locale.instance.SocksProxy)) proxyPort.setText("1080");
        });

        LanguageButton.setOnMouseClicked((MouseEvent event) -> {
            if (comboBoxLanguage.getValue().equals("中文简体")) {
                Locale.load("zh");
                Config.instance.lang = "zh";
            } else if (comboBoxLanguage.getValue().equals("English")) {
                Locale.load("en");
                Config.instance.lang = "en";
            }
            SettingsController.instance.setLocale();
            FrameController.instance.setSkin(Config.instance.skin);
            Config.save();
        });

        connect.setOnMouseClicked((MouseEvent event) -> {
            if (comboBoxProxy.getValue() == null) {
                new Popup().display(Locale.instance.Error, Locale.instance.ErrorMessage);
            } else if (proxyHost.getText().equals("")) {
                new Popup().display(Locale.instance.Error, Locale.instance.ErrorMessage);
            } else if (proxyUser.getText().equals("")) {
                new Popup().display(Locale.instance.Error, Locale.instance.ErrorMessage);
            } else if (proxyPassword.getText().equals("")) {
                new Popup().display(Locale.instance.Error, Locale.instance.ErrorMessage);
            } else if (comboBoxProxy.getValue().equals(Locale.instance.HttpProxy)) {
                Properties prop = System.getProperties();
                prop.setProperty("http.proxyHost", proxyHost.getText());
                prop.setProperty("http.proxyPort", proxyPort.getText());
                Config.instance.proxyHost = proxyHost.getText();
                Config.instance.proxyPort = proxyPort.getText();
                Config.instance.proxyMode = "http";
            } else if (comboBoxProxy.getValue().equals(Locale.instance.SocksProxy)) {
                Properties prop = System.getProperties();
                prop.setProperty("socksProxyHost", proxyHost.getText());
                prop.setProperty("socksProxyPort", proxyPort.getText());
                Config.instance.proxyHost = proxyHost.getText();
                Config.instance.proxyPort = proxyPort.getText();
                Config.instance.proxyMode = "socks";
            }
            Authenticator.setDefault(new MyAuthenticator(proxyUser.getText(), proxyPassword.getText()));
            Config.instance.proxyUser = proxyUser.getText();
            Config.save();
        });

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                Config.instance.name = name.getText();
                FrameController.instance.username.setText(name.getText());
                Config.save();
            }
        });
    }

    /**
     * 设置语言
     */
    public void setLocale() {
        chooeFile.setText(Locale.instance.chooeFile);
        connect.setText(Locale.instance.connect);
        checkUpdate.setText(Locale.instance.checkUpdate);
        Language.setText(Locale.instance.Language);
        Setting.setText(Locale.instance.Setting);
        LaunchSetting.setText(Locale.instance.LaunchSetting);
        GameSettings.setText(Locale.instance.GameSettings);
        NetworkSetting.setText(Locale.instance.NetworkSetting);
        PlayerName.setText(Locale.instance.PlayerName);
        JavaPath.setText(Locale.instance.JavaPath);
        ProxyMode.setText(Locale.instance.ProxyMode);
        ProxyHost.setText(Locale.instance.ProxyHost);
        ProxyPort.setText(Locale.instance.ProxyPort);
        ProxyUserName.setText(Locale.instance.ProxyUserName);
        ProxyPassword.setText(Locale.instance.ProxyPassword);

        if (Config.instance.proxyMode == null) comboBoxProxy.setValue(null);
        else if (Config.instance.proxyMode.equals("http"))
            comboBoxProxy.setValue(Locale.instance.HttpProxy);
        else comboBoxProxy.setValue(Locale.instance.SocksProxy);

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
