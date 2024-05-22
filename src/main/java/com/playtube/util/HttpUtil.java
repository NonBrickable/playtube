package com.playtube.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http请求工具类
 */
public class HttpUtil {

    private static final String REQUEST_METHOD_GET = "GET";
    private static final Integer CONNECT_TIME_OUT = 120000;

    /**
     * http get请求
     * @param url      请求链接
     * @param headers  请求头
     * @param response 响应
     */
    public static void get(String url,
                                   Map<String, Object> headers,
                                   HttpServletResponse response) throws Exception {
        HttpURLConnection con = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            URL urlObj = new URL(url);
            con = (HttpURLConnection) urlObj.openConnection();
            con.setDoInput(true);
            con.setRequestMethod(REQUEST_METHOD_GET);
            con.setConnectTimeout(CONNECT_TIME_OUT);
            for (Entry<String, Object> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
            con.connect();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
                bis = new BufferedInputStream(con.getInputStream());
                os = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                //刷新输出流，否则不会写出数据
                os.flush();
            } else {
                response.setStatus(responseCode);
            }
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (con != null) {
                con.disconnect();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}