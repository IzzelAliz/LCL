package me.kevinwalker.utils.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Updater {

    private static String version, commit;


    static {
        JsonObject object = new JsonParser().parse(new InputStreamReader(
                Updater.class.getResourceAsStream("/version.json"))).getAsJsonObject();
        version = object.get("current").getAsString();
        commit = object.get("commit").getAsString();
    }

    public static String newVersion = version, newCommit = commit;

    public static String currentVersion() {
        return version;
    }

    public static String currentCommit() {
        return commit;
    }

    public static String checkUpdate() {
        try {
            URL url = new URL("https://api.github.com/repos/IzzelAliz/LCL/releases/latest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JsonObject object = new JsonParser().parse(reader.readLine()).getAsJsonObject();
            newVersion = object.get("tag_name").getAsString();
            newCommit = object.get("body").getAsString();
            return newVersion + "/" + newCommit;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
