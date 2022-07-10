package org.ce.ap.discord.server;

import org.ce.ap.discord.common.boot.Bootstrapper;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.boot.BootstrapProperties;
import org.ce.ap.discord.common.entity.business.ServerInformation;
import org.ce.ap.discord.common.util.io.ObjectIOHelper;
import org.ce.ap.discord.common.util.logger.Logger;
import org.ce.ap.discord.server.boot.ServerBootstrapper;
import org.ce.ap.discord.server.network.ApplicationServer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Parham Ahmady
 * @since 27/5/2022
 */
public class ServerApplicationLoader {

    private static final Logger LOGGER = Logger.getLogger(ServerApplicationLoader.class.getName());

    public static void main(String[] args) throws InterruptedException, IOException {
        Bootstrapper bootstrapper = new ServerBootstrapper("DiscordServer");
        BootstrapProperties bootstrapProperties = new BootstrapProperties("DiscordServer", List.of("server.properties"));
        bootstrapper.initialize(bootstrapProperties);
        bootstrapper.initializingPostProcessor();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        ApplicationServer server = (ApplicationServer) applicationContext.getApplicationBeans().get(ApplicationServer.class);
        server.initialize();
        Thread serverThread = new Thread(server);
        serverThread.start();

        while (true) {
            System.out.println("To Exit enter e");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            if (command.equals("e")) {
                try {
                    shutdownHook(applicationContext);
                    //noinspection deprecation
                    serverThread.stop();
                    server.shutdown();
                    System.exit(9);
                    break;
                } catch (InterruptedException e) {
                    LOGGER.error("Cant Shutdown Server");
                }
            }
        }
    }

    private static void shutdownHook(ApplicationContext applicationContext) {
        ServerInformation serverInformation = (ServerInformation) applicationContext.getApplicationBeans().get(ServerInformation.class);
        ObjectIOHelper ioHelper = (ObjectIOHelper) applicationContext.getApplicationBeans().get(ObjectIOHelper.class);
        boolean successful = ioHelper.saveObject("INFO", serverInformation, "serverInformation.bin");
        if (successful) {
            LOGGER.info("ServerInformation saved");
        } else
            LOGGER.warn("Couldn't save ServerInformation");
    }
}
