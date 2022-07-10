package org.ce.ap.discord.common.entity.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Parham Ahmady
 * @since 30/5/2022
 */
@SuppressWarnings("rawtypes")
public class ApplicationContext {
    private static ApplicationContext applicationContext;
    private final Map<Class, Object> applicationBeans;
    private Properties applicationProperties;

    private ApplicationContext() {
        applicationBeans = new HashMap<>();
    }

    public Map<Class, Object> getApplicationBeans() {
        return applicationBeans;
    }

    public void addBean(Class clazz, Object object) {
        applicationBeans.put(clazz, object);
    }

    public Properties getApplicationProperties() {
        return applicationProperties;
    }

    public void addProperties(Properties newProperties) {
        this.applicationProperties = newProperties;
    }

    public static ApplicationContext getInstance() {
        if (applicationContext == null)
            applicationContext = new ApplicationContext();
        return applicationContext;
    }
}
