package com.leaihealth.cloud.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post请求
     *
     * @param url
     * @param map
     * @return
     */
    public static String httpPost(String url, Map<String, Object> map, String reuqestMethod, String json) {
        try {
            HttpPost post = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(50000).build();
            post.setConfig(requestConfig);
            if ("payload".equals(reuqestMethod)) {
                post.setHeader("Accept", "application/json");
                post.setHeader("content-type", "application/json");

                StringEntity stringEntity = new StringEntity(json, "utf-8");

                post.setEntity(stringEntity);
            } else {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (entry.getValue() != null && entry.getValue() != "") {
                        params.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
                    }
                }
                post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            }
            logger.info("请求url:" + url);
            logger.info("请求数据:" + map);
            CloseableHttpResponse httpResponse = HttpClients.createDefault().execute(post);
            System.out.println("返回数据：" + httpResponse);
            String result = null;
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);// 取出应答字符串
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonResponse getData(String url, Map<String, Object> map) {
        String result = httpPost(url, map, "", "");
        if (StringUtils.isNotEmpty(result)) {
            Gson gson = new Gson();
            JsonResponse response = gson.fromJson(result, JsonResponse.class);
            return response;
        } else {
            return null;
        }
    }

}
