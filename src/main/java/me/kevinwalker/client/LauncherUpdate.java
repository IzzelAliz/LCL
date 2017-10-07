package me.kevinwalker.client;

import me.kevinwalker.utils.Util;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class LauncherUpdate {


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
