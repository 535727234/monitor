package tool;


import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpClientTool {
    /**
     * 发送 get请求
     */
    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = null;
        try {
            httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String body = EntityUtils.toString(entity);
                    return body;
                } else {
                    LoggerUtils.warn("get uri entity is empty, uri:" + httpget.getURI() + response.getStatusLine());
                }
            } finally {
                response.close();
            }
        } catch ( ParseException | IOException e) {
            LoggerUtils.error("get url error" + httpget.getURI(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LoggerUtils.error("close connection error", e);
            }
        }
        return null;
    }

    /**
     * post request
     *
     * @param url
     * @param header
     * @param body
     * @return
     */
    public static String post(String url, Map<String, String> header, String body) {
        HttpPost request = new HttpPost(url);
        return httpRequest(request,body);
    }
    public static String put(String url,String body){
        HttpEntityEnclosingRequestBase request = new HttpPut(url);
        return httpRequest(request,body);
    }
    public static String httpRequest(HttpEntityEnclosingRequestBase request,String body){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        request.setHeader("Accept", "application/json");
        try {
            request.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
            CloseableHttpResponse response = httpclient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            } finally {
                response.close();
            }
        } catch (IOException e) {
            LoggerUtils.error("http put error",e);
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LoggerUtils.error("http client close error",e);
            }
        }
        return "";
    }

}