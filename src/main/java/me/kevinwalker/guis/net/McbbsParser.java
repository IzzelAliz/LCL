package me.kevinwalker.guis.net;

import java.io.IOException;
import java.util.List;

import javafx.scene.paint.Color;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class McbbsParser {

	public static List<Container> parse(String... param) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		try (CloseableHttpClient client = builder.build()) {
			String url = "http://www.mcbbs.net/forum.php?";
			for (String para : param)
				url += para + "&";
			url += "mod=forumdisplay&mobile=2";
			HttpGet get = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).build();
			get.setConfig(requestConfig);
			get.setHeader("User-Agent",
					"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Mobile Safari/537.36");
			get.setHeader("Connection", "keep-alive");
			try (CloseableHttpResponse response = client.execute(get)) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String html = EntityUtils.toString(entity, "utf-8");
					System.out.println(html);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public class Container {
		String title, url;
		int reply;
		Color color;
	}
}
