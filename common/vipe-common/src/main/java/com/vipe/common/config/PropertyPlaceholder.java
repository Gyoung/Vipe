package com.vipe.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zengjiyang on 2016/5/16.
 */
public final class PropertyPlaceholder extends PropertyPlaceholderConfigurer {
    private static Map<String, String> ctxPropertiesMap;

    public static String getProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public static String getPropertyOrDefault(String name, String defaultValue) {
        return ctxPropertiesMap.getOrDefault(name, defaultValue);
    }

    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

}
