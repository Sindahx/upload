package com.chrl.bs.sfs.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 利用百度api 进行翻译
 * Created by cat(chrl) on 2015/6/19.
 */
public class URLTools {

    private final static String apiKey = "5e0a700c2da377d56fffabaa40290275";
    private final static String httpUrl = "http://apis.baidu.com/apistore/tranlateservice/translate";

    public static String translate(String text) throws UnsupportedEncodingException {
        text = URLEncoder.encode(text,"UTF-8");
        String httpArg = "query=" + text + "&from=zh&to=en";
        String jsonResult = request(httpUrl, httpArg);
        JSONObject jsonObject = JSONObject.fromObject(jsonResult);
        String str = ((JSONObject)((JSONArray)((JSONObject)(jsonObject.get("retData"))).get("trans_result")).get(0)).get("dst").toString();
        byte[] converttoBytes = str.getBytes("UTF-8");
        return new String(converttoBytes, "UTF-8");
    }


    /**
     * @param httpUrl :����ӿ�
     * @param httpArg :����
     * @return ���ؽ��
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // ����apikey��HTTP header
            connection.setRequestProperty("apikey", apiKey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead ;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
