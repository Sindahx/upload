package com.leaihealth.cloud.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * ClassName: com.leaihealth.cloud.util.JsonUtils
 * <p>
 * Auth: huangxing
 * <p>
 * Date: 2019/3/3
 * <p>
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
public class JsonUtils {

    public static Gson gson = null;

    public static Gson getJson(){
        if (gson == null){
            gson = new Gson();
//            gson = new GsonBuilder().serializeNulls().create();
        }
        return gson;
    }
}
