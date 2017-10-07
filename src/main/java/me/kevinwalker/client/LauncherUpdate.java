package me.kevinwalker.client;

import me.kevinwalker.main.Main;
import me.kevinwalker.utils.AsyncDownload;
import me.kevinwalker.utils.Util;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LauncherUpdate {

    static String url;

    public static void download() {
        AsyncDownload.download(url, new File(Main.getBaseDir(), "update.jar"), 4, new AsyncDownload.Callback() {
            @Override
            public void log(String msg) {
            }
            @Override
            public void setProgress(double progress) {
            }
            @Override
            public void setTooltip(String tooltip) {
            }
            @Override
            public void setSpeed(String speed) {
            }
            @Override
            public void success(HashMap<String, Object> obj) {
            }
            @Override
            public void fail(HashMap<String, Object> obj) {
            }
        });
    }

    public static boolean check() {
        String latest = null;
        HttpClientBuilder builder = HttpClientBuilder.create();
        try (CloseableHttpClient httpClient = builder.build()) {
            String url = "http://lcl.ilummc.com/version.json";
            HttpGet get = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).build();
            get.setConfig(requestConfig);
            get.setHeader("Connection", "keep-alive");
            try (CloseableHttpResponse response = httpClient.execute(get)) {
                String json = EntityUtils.toString(response.getEntity());
                JSONObject obj = new JSONObject(new JSONTokener(json));
                latest = obj.getString("latest");
                url = "http://lcl.ilummc.com/" + obj.getString("link");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentJson = new String(Util.loadResource("version.json"));
        JSONObject obj = new JSONObject(new JSONTokener(currentJson));
        String current = obj.getString("version");
        return latest.equals(current);
    }
}
