package org.ce.ap.discord.client;

import org.ce.ap.discord.client.boot.ClientBootStrapper;
import org.ce.ap.discord.client.business.CommandParser;
import org.ce.ap.discord.common.boot.Bootstrapper;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.boot.BootstrapProperties;

import java.io.IOException;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public class ClientApplicationLoader {

    public static void main(String[] args) throws InterruptedException, IOException {
        Bootstrapper bootstrapper = new ClientBootStrapper("DiscordClient");
        BootstrapProperties bootstrapProperties = new BootstrapProperties("DiscordClient", List.of("client.properties"));
        bootstrapper.initialize(bootstrapProperties);
        bootstrapper.initializingPostProcessor();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        CommandParser commandParser = (CommandParser) applicationContext.getApplicationBeans().get(CommandParser.class);
        commandParser.start();
    }
}
