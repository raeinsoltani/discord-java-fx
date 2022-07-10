package org.ce.ap.discord.common.boot;

import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.boot.BootstrapProperties;
import org.ce.ap.discord.common.util.io.ClassPathLoader;
import org.ce.ap.discord.common.util.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Parham Ahmady
 * @since 27/5/2022
 */
public interface Bootstrapper {

    Logger LOGGER = Logger.getLogger(Bootstrapper.class.getName());

    void initialize(BootstrapProperties properties);

    @SuppressWarnings("rawtypes")
    default void initializingPostProcessor() throws InterruptedException {
        LOGGER.info("Boot PostProcessor Starts");
        final ApplicationContext context = ApplicationContext.getInstance();
        final Map<Class, Object> applicationBeans = context.getApplicationBeans();
        for (Class clazz : applicationBeans.keySet()) {
            Thread.sleep(100);
            Object bean = applicationBeans.get(clazz);
            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet(context);
            }
        }
        LOGGER.info("Boot PostProcessing finished");
    }

    default void loadProperties(ApplicationContext applicationContext, BootstrapProperties bootProperties, ClassPathLoader classPathLoader) {
        List<String> propertiesSources = bootProperties.getPropertiesSources();
        Properties loadedProperties = new Properties();
        propertiesSources.forEach(source -> {
            File sourceFile =new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(source)).getFile());
            if (!sourceFile.exists()) {
                LOGGER.warn("Cant Load Properties Source {}", sourceFile.getPath());
                return;
            }
            try {
                loadedProperties.load(new FileInputStream(sourceFile));
            } catch (IOException e) {
                LOGGER.warn("Cant Load Properties Source {}", sourceFile.getPath());
            }
        });
        applicationContext.addProperties(loadedProperties);
    }
}
