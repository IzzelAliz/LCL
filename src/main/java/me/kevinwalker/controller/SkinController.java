package me.kevinwalker.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.kevinwalker.guis.GuiBase;
import me.kevinwalker.main.ConfigController;
import me.kevinwalker.main.Main;
import me.kevinwalker.utils.ZipUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by KevinWalker on 2017/10/7.
 */
public class SkinController extends MainController {
    List<String> skinList;

    @FXML ImageView previewImg;
    @FXML Button skinBtn;
    @FXML ComboBox chooseSkin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        guisettings();
    }
    /**
     * 界面配置
     */
    void guisettings() {
        LoginCraftLaunchController.onGuiOpen(skinBtn);

        //选择控件获取文件名List
        skinList = getFileList(new File(Main.getBaseDir(),"LclConfig/skin"));
        chooseSkin.setValue(ConfigController.json.getString("skin"));
        for(String name : skinList) {
            chooseSkin.getItems().add(name);
        }

        skinBtn.setOnAction(oa -> {
            ConfigController.json.put("skin",chooseSkin.getValue());
            ConfigController.saveJson();
            Main.mainGui = new GuiBase("LoginCraftLaunch", Main.primaryStage, 800, 530);
            Main.mainGui.show();
        });

        ZipUtils zipFile = new ZipUtils(ConfigController.json.getString("skin"));
        previewImg.setImage(new Image(zipFile.getInputStream("preview.png")));
        try {
            zipFile.inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        chooseSkin.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                ZipUtils zipFile2 = new ZipUtils((String)chooseSkin.getValue());
                previewImg.setImage(new Image(zipFile.getInputStream("preview.png")));
                try {
                    zipFile2.inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取材质文件
     * @param file 文件位置
     * @return 文件名List
     */
    public static List<String> getFileList(File file) {
        List<String> result = new ArrayList<String>();
        if (!file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            result.add(file.getAbsolutePath());
        } else {
            File[] directoryList = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().indexOf("zip") > -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < directoryList.length; i++) {
                result.add(directoryList[i].getName().substring(0,directoryList[i].getName().length()-4));
            }
        }
        return result;
    }
}
