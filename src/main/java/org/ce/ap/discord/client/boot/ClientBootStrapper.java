package org.ce.ap.discord.client.boot;

import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.client.business.display.entity.MainMenu;
import org.ce.ap.discord.client.business.display.impl.DisplayServiceImpl;
import org.ce.ap.discord.client.business.network.DiscordClient;
import org.ce.ap.discord.client.business.network.impl.ClientNetworkServiceManagementImpl;
import org.ce.ap.discord.common.boot.Bootstrapper;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.boot.BootstrapProperties;
import org.ce.ap.discord.common.util.io.ClassPathLoader;
import org.ce.ap.discord.common.util.logger.Logger;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public class ClientBootStrapper implements Bootstrapper {

    private static final Logger LOGGER = Logger.getLogger(ClientBootStrapper.class.getName());

    private final String applicationName;
    private ApplicationContext applicationContext;

    public ClientBootStrapper(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void initialize(BootstrapProperties bootProperties) {
        LOGGER.info("Starting to Boot {} application", applicationName);
        applicationContext = ApplicationContext.getInstance();
        ClassPathLoader classPathLoader = new ClassPathLoader();
        loadProperties(applicationContext, bootProperties, classPathLoader);
        loadApplicationBeans();
        LOGGER.info("Boot initialization complete");
    }

    private void loadApplicationBeans() {
        applicationContext.addBean(DiscordClient.class, new DiscordClient());
        applicationContext.addBean(CommandParser.class, new CommandParser());
        applicationContext.addBean(DisplayServiceImpl.class, new DisplayServiceImpl(new MainMenu()));
        applicationContext.addBean(ClientNetworkServiceManagementImpl.class, new ClientNetworkServiceManagementImpl());
    }
}
