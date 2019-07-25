/**
 * ClassName: SystemConfig.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/06
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * System configuration.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/06
 */
@Configuration
//@PropertySource(value = {"file:../conf/idemService.properties", "classpath:message_zh_CN.properties", "file:../conf/interface.properties"})
@PropertySource(value = {"file:${icem.home}/idemService.properties"})
public class SystemConfig {

    public static final String PROP_SESSION_TIMEOUT = "session.timeout";

    private static Environment env;

    @Autowired
    private Environment autoWiredEnv;

    public static Map<String, String> dataConfig = new HashMap<>();

    public static Map<String, String> commData = new HashMap<>();


    @PostConstruct
    private void init() {
        env = this.autoWiredEnv;
    }

    public static int getInteger(final String propName) {
        if ((env == null) || StringUtils.isBlank(propName)) {
            return -1;
        }

        return Integer.valueOf(env.getProperty(propName, "-1"));
    }

    public static String getString(final String propName) {
        if ((env == null) || StringUtils.isBlank(propName)) {
            return "";
        }

        return env.getProperty(propName, "");
    }

    public static String getCommData(String key) {
        if (commData.containsKey(key)) {
            return commData.get(key);
        } else {
            return null;
        }

    }

    public static void setCommData(String key, String value) {
        commData.put(key, value);
    }

}
