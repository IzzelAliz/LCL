package me.kevinwalker.guis.net;

import javafx.scene.paint.Color;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class McbbsParser {

    public static enum PARAM {
        /**
         * 材质版
         */
        FORUM_TEXTURE("fid=44"),
        /**
         * 皮肤版
         */
        FORUM_SKIN("fid=169"),
        /**
         * Mod版
         */
        FORUM_MOD("fid=45"),
        /**
         * 插件版
         */
        FORUM_PLUGIN("fid=138"),
        /**
         * 页数，使用 page(int 页数) 方法
         */
        PAGE(""),
        /**
         * 热度排序
         */
        FILTER_HEAT("filter=heat&orderby=heats"),
        /**
         * 仅精华
         */
        FILTER_DIGEST("filter=digest&digest=1"),
        /**
         * 一段时间的热度，貌似无效
         */
        FILTER_HOT("filter=hot"),
        /**
         * 最新发帖
         */
        FILTER_LATEST("filter=author&orderby=dateline"),
        /**
         * 查看量
         */
        FILTER_VIEW("filter=reply&orderby=views"),
        /**
         * 回复量
         */
        FILTER_REPLY("filter=reply&orderby=replies"),
        /**
         * 时间段内，使用 time(long 秒)方法
         */
        FILTER_TIME("");

        String v;

        PARAM(String s) {
            v = s;
        }

        public String value() {
            return v;
        }

        public String time(long seconds) {
            return "dateline=" + seconds;
        }

        public String page(int page) {
            return "page=" + page;
        }
    }

    public static List<ThreadPost> parse(String[] param) {
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
                    return parseXml(html);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static List<ThreadPost> parseXml(String xml) {
        List<ThreadPost> list = new ArrayList<>();
        try {
            Document document = Jsoup.parse(xml);
            Elements htmle = document.select("html");
            Document html = Jsoup.parse(htmle.toString());
            Elements bodye = html.select("body");
            Document body = Jsoup.parse(bodye.toString());
            body.getElementsByClass("threadlist").stream().forEach(div -> {
                if (div == null) {
                    System.out.println("0");
                    return;
                }
                div.select("ul").select("li").stream().forEach(li -> {
                    ThreadPost tp = new ThreadPost();
                    tp.url = parseUrl(li.select("a").attr("href"));
                    tp.author = li.select("a").select("span").text().trim();
                    tp.title = li.select("a").text().trim();
                    tp.title = tp.title.substring(0, tp.title.length() - tp.author.length());
                    if (li.select("a").attr("style").contains("color: ")) {
                        String style = li.select("a").attr("style");
                        style = style.substring(style.indexOf("color: #") + "color: #".length());
                        style = style.substring(0, style.indexOf(";"));
                        tp.color = Color.web(style, 1.0D);
                    }
                    li.getElementsByTag("span").stream().forEach(span -> {
                        if (span.attr("class").equals("num"))
                            tp.reply = Integer.parseInt(span.text().trim());
                        if (span.attr("class").equals("icon_tu")) tp.picture = true;
                        if (span.attr("class").equals("icon_top")) {
                            if (span.select("img").attr("src").contains("digest"))
                                tp.digest = true;
                            else tp.top = true;
                        }
                    });
                    if (!tp.top)
                        list.add(tp);
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    static String parseUrl(String mobile) {
        String tid = mobile.substring(mobile.indexOf("tid=") + 4);
        tid = tid.substring(0, tid.indexOf("&"));
        return "http://www.mcbbs.net/thread-" + tid + "-1-1.html";
    }

    public static class ThreadPost {
        public String title, url, author;
        public int reply;
        public Color color = new Color(0.0D, 0.0D, 0.0D, 1.0D);
        public boolean picture = false, digest = false, top = false;
    }
}
