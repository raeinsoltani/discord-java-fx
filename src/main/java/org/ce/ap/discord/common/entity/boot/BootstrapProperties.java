package org.ce.ap.discord.common.entity.boot;

import java.util.List;

/**
 * @author Parham Ahmady
 * @since 30/5/2022
 */
public class BootstrapProperties {
    private final String applicationName;
    private final List<String> propertiesSources;

    public BootstrapProperties(String applicationName, List<String> propertiesSources) {
        this.applicationName = applicationName;
        this.propertiesSources = propertiesSources;
    }

    public List<String> getPropertiesSources() {
        return propertiesSources;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
