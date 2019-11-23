package cn.dintalk.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Mr.song
 * @date 2019/10/13 9:26
 */
public class WebUtils {

    /**
     * 根据url和参数发送get请求
     *
     * @param url
     * @param param
     * @return 返回网页内容
     */
    public static String sendGet(String url, String param) {
        String result = "";
        if (param != null) {
            url = url + "?" + param;
        }
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = getHttpURLConnection(realUrl);
            result = getResponse(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //根据url 获取连接
    private static HttpURLConnection getHttpURLConnection(URL realUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        sb.append(" AppleWrbKit/537.36(KHTML, like Gecko)");
        sb.append(" Chrome/72.0.3626.119 Safari/537.36");
        HttpURLConnection conn = null;
        try {
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 根据url连接获取响应
    private static String getResponse(HttpURLConnection conn) {
        // 读取URL的响应
        String result = "";
        try (InputStream is = conn.getInputStream();
             InputStreamReader isr = new InputStreamReader(is, "utf-8");
             BufferedReader in = new BufferedReader(isr)) {
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("Err:getResponse()");
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
//        System.out.println("getResponse()：" + result.length());
        return result;
    }

    /**
     * 解析网页为文本
     *
     * @param html
     * @return
     */
    public static String parseHtmlToText(String html) {
        Document document = Jsoup.parse(html);
        return document.text();
    }
}
