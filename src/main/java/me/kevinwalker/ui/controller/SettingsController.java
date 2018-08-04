package me.kevinwalker.ui.controller;

import com.sun.management.OperatingSystemMXBean;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import me.kevinwalker.main.Config;
import me.kevinwalker.main.Locale;
import me.kevinwalker.main.Main;
import me.kevinwalker.ui.Popup;
import me.kevinwalker.utils.Util;
import me.kevinwalker.utils.io.Updater;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class SettingsController implements Initializable {
    public static SettingsController instance;
    private static ExecutorService service = Executors.newFixedThreadPool(1);

    public VBox settingPane;

    public Text Setting;
    public Text GameSettings, LaunchSetting, NetworkSetting;
    public Text PlayerName, GamePath, JavaPath, Language, ProxyMode, ProxyHost, ProxyPort, ProxyUserName, ProxyPassword, PhysicalMemory, MaxMemory;

    public TextField name, maxMemory;
    public TextField gamePath, javaPath;
    public TextField proxyHost, proxyPort, proxyUser, proxyPassword;

    public ComboBox comboBoxLanguage, comboBoxProxy;

    public Text VersionInfo;

    public Button checkUpdate, connect, LanguageButton;
    public Button chooeFile, chooeFile1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        setLocale();
        javaPath.setText(Config.instance.javaPath);
        gamePath.setText(Config.instance.gamePath);
        name.setText(Config.instance.name);
        maxMemory.setText(Config.instance.maxMemory);
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        PhysicalMemory.setText(Locale.instance.PhysicalMemory + ":" + String.valueOf(osmxb.getTotalPhysicalMemorySize() / (1024 * 1024)) + "MB");

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

        chooeFile1.setOnAction(oa -> {
            DirectoryChooser folderChooser = new DirectoryChooser();
            folderChooser.setTitle(Locale.instance.GamePath);
            folderChooser.setInitialDirectory(Util.getBaseDir());
            File selectedFile = folderChooser.showDialog(Main.primaryStage);
            gamePath.setText(selectedFile.getPath());
            Config.instance.gamePath = selectedFile.getPath();
            Config.save();
        });

        VersionInfo.setText("当前版本 " + Updater.currentVersion() + "/" + Updater.currentCommit());
        checkUpdate.setOnMouseClicked((MouseEvent event) -> service.execute(() -> {
            String v = Updater.checkUpdate();
            Platform.runLater(() -> VersionInfo.setText("最新版本 " + v));
        }));

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
                if (name.getText() != null && !name.getText().equals("") && Pattern.compile("^[A-Za-z][A-Za-z1-9_-]+$").matcher(name.getText()).matches()) {
                    Config.instance.name = name.getText();
                    FrameController.instance.username.setText(name.getText());
                    Config.save();
                } else {
                    new Popup().display(Locale.instance.Error, Locale.instance.ErrorMessage);
                    name.setText("Administrator");
                }
            }
        });

        maxMemory.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (maxMemory.getText() != null && !maxMemory.getText().equals("") && Pattern.compile("^[0-9]*$").matcher(maxMemory.getText()).matches()) {
                    Config.instance.maxMemory = maxMemory.getText();
                    Config.save();
                } else {
                    new Popup().display(Locale.instance.Error, Locale.instance.ErrorMessage);
                    maxMemory.setText("1024");
                }
            }
        });
        setGuiColor(settingPane.getChildren(), Main.awtTextColor);
    }

    private void setGuiColor(ObservableList<Node> list, java.awt.Color awtColor) {
        for (Node node : list) {
            node.setStyle("-fx-text-fill: rgba(" + awtColor.getRed() + "," + awtColor.getGreen() + "," + awtColor.getBlue() + ",1);");
            if (node instanceof Text) {
                ((Text) node).setFill(Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()));
            }else if(node instanceof Button){
                node.setStyle("-fx-text-fill: rgba(255,255,255,1);");
            } else if (node instanceof TextField) {
                node.setStyle("-fx-text-fill: rgba(" + awtColor.getRed() + "," + awtColor.getGreen() + "," + awtColor.getBlue() + ",1);" +
                        "-fx-border-color: rgba(0, 0, 0, 0) " +
                        "rgba(0, 0, 0, 0)" +
                        "rgba(" + awtColor.getRed() + "," + awtColor.getGreen() + "," + awtColor.getBlue() + ")" +
                        "rgba(0, 0, 0, 0);");
            } else if (node instanceof Pane) {
                setGuiColor(((Pane) node).getChildren(), awtColor);
            }
        }
    }

    /**
     * 设置语言
     */
    public void setLocale() {
        chooeFile.setText(Locale.instance.chooeFile);
        chooeFile1.setText(Locale.instance.chooeFile);
        connect.setText(Locale.instance.connect);
        checkUpdate.setText(Locale.instance.checkUpdate);
        Language.setText(Locale.instance.Language);
        Setting.setText(Locale.instance.Setting);
        LaunchSetting.setText(Locale.instance.LaunchSetting);
        GameSettings.setText(Locale.instance.GameSettings);
        NetworkSetting.setText(Locale.instance.NetworkSetting);
        PlayerName.setText(Locale.instance.PlayerName);
        GamePath.setText(Locale.instance.GamePath);
        JavaPath.setText(Locale.instance.JavaPath);
        ProxyMode.setText(Locale.instance.ProxyMode);
        ProxyHost.setText(Locale.instance.ProxyHost);
        ProxyPort.setText(Locale.instance.ProxyPort);
        ProxyUserName.setText(Locale.instance.ProxyUserName);
        ProxyPassword.setText(Locale.instance.ProxyPassword);
        MaxMemory.setText(Locale.instance.MaxMemory);

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
