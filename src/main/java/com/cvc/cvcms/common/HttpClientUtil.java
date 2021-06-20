package com.cvc.cvcms.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 * 使用原型模式减少代码重复
 * @author ZZJ
 */
@Slf4j
public class HttpClientUtil {

    public String doGet(String url, Map<String, String> param) {
        DoGetHttpClient doGetHttpClient = new DoGetHttpClient();
        return doGetHttpClient.doMain(url,param);
    }

    public String doGet(String url) {
        return doGet(url, null);
    }

    public String doPost(String url, Map<String, String> param) {
        DoPostHttpClient doPostHttpClient = new DoPostHttpClient();
        return doPostHttpClient.doMain(url,param);
    }

    public String doPost(String url) {
        return doPost(url, null);
    }

    public String doPostJson(String url, String json) {
        DoPostJsonHttpClient doPostJsonHttpClient = new DoPostJsonHttpClient();
        HashMap<String, String> param = new HashMap<>(1);
        param.put("json",json);
        return doPostJsonHttpClient.doMain(url,param);
    }

    private abstract class HttpClientPrototype{
        CloseableHttpClient httpClient;
        CloseableHttpResponse response;
        String resultString;

        /**
         * 核心
         */
        abstract void doCore(String url,Map<String, String> param) throws IOException, URISyntaxException;

        public final String doMain(String url, Map<String, String> param){
            httpClient = HttpClients.createDefault();
            resultString = "";
            try {

                doCore(url, param);

            } catch (Exception e) {
                log.error("http请求异常",e);
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    log.error("CloseableHttpResponse关闭异常",e);
                }
            }

            return resultString;
        }
    }

    private class DoGetHttpClient extends HttpClientPrototype{

        @Override
        void doCore(String url, Map<String, String> param) throws IOException, URISyntaxException {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }
    }

    private class DoPostHttpClient extends HttpClientPrototype{

        @Override
        void doCore(String url, Map<String, String> param) throws IOException, URISyntaxException {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }
    }

    private class DoPostJsonHttpClient extends HttpClientPrototype{

        /**
         * post json出去
         * param 必须带有一个key："json"
         * @param url
         * @param param 必须带有一个key："json"
         * @throws IOException
         * @throws URISyntaxException
         */
        @Override
        void doCore(String url, Map<String, String> param) throws IOException, URISyntaxException {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(param.get("json"), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        }
    }


}