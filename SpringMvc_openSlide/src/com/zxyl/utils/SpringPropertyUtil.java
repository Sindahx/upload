package com.zxyl.utils;

/**
 * Created by Administrator on 2018/3/15.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SpringPropertyUtil extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    @SuppressWarnings("unchecked")
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Iterator localIterator = props.keySet().iterator(); localIterator
                .hasNext(); ) {
            Object key = localIterator.next();
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public Map<String, Object> getResolvedProps() {
        return ctxPropertiesMap;
    }

    @SuppressWarnings("static-access")
    public void setResolvedProps(Map<String, Object> ctxPropertiesMap) {
        this.ctxPropertiesMap = ctxPropertiesMap;
    }
}