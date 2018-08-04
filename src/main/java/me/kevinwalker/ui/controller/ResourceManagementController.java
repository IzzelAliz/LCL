package me.kevinwalker.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import me.kevinwalker.main.Locale;

import java.net.URL;
import java.util.ResourceBundle;

public class ResourceManagementController implements Initializable {
    @FXML
    public TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tab configTab = new Tab();
        configTab.setText(Locale.instance.config);
        configTab.setContent(new AnchorPane());
        Tab crashReportsTab = new Tab();
        crashReportsTab.setText(Locale.instance.crashReports);
        crashReportsTab.setContent(new AnchorPane());
        Tab logsTab = new Tab();
        logsTab.setText(Locale.instance.logs);
        logsTab.setContent(new AnchorPane());
        Tab modsTab = new Tab();
        modsTab.setText(Locale.instance.mods);
        modsTab.setContent(new AnchorPane());
        Tab resourcepacksTab = new Tab();
        resourcepacksTab.setText(Locale.instance.resourcepacks);
        resourcepacksTab.setContent(new AnchorPane());
        Tab savesTab = new Tab();
        savesTab.setText(Locale.instance.saves);
        savesTab.setContent(new AnchorPane());
        Tab screenshotsTab = new Tab();
        screenshotsTab.setText(Locale.instance.screenshots);
        screenshotsTab.setContent(new AnchorPane());
        Tab shaderpacksTab = new Tab();
        shaderpacksTab.setText(Locale.instance.shaderpacks);
        shaderpacksTab.setContent(new AnchorPane());

        tabPane.getTabs().add(configTab);
        tabPane.getTabs().add(crashReportsTab);
        tabPane.getTabs().add(logsTab);
        tabPane.getTabs().add(modsTab);
        tabPane.getTabs().add(resourcepacksTab);
        tabPane.getTabs().add(savesTab);
        tabPane.getTabs().add(screenshotsTab);
        tabPane.getTabs().add(shaderpacksTab);
    }
}
