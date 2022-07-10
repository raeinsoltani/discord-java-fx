package org.ce.ap.discord.server.boot;

import org.ce.ap.discord.common.boot.Bootstrapper;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.boot.BootstrapProperties;
import org.ce.ap.discord.common.entity.business.ServerInformation;
import org.ce.ap.discord.common.util.io.ClassPathLoader;
import org.ce.ap.discord.common.util.io.ObjectIOHelper;
import org.ce.ap.discord.common.util.io.RootDirectoryIOHelper;
import org.ce.ap.discord.common.util.logger.Logger;
import org.ce.ap.discord.server.business.authentication.impl.AuthenticationServiceImpl;
import org.ce.ap.discord.server.business.authentication.wrapper.impl.AuthenticationServiceWrapperImpl;
import org.ce.ap.discord.server.business.discord.impl.ChatManagementServiceImpl;
import org.ce.ap.discord.server.business.discord.impl.ServerManagementServiceImpl;
import org.ce.ap.discord.server.business.discord.impl.UserManagementServiceImpl;
import org.ce.ap.discord.server.business.discord.wrapper.impl.ChatManagementServiceWrapperImpl;
import org.ce.ap.discord.server.business.discord.wrapper.impl.ServerManagementServiceWrapperImpl;
import org.ce.ap.discord.server.business.discord.wrapper.impl.UserManagementServiceWrapperImpl;
import org.ce.ap.discord.server.network.ApplicationServer;

import java.io.File;

/**
 * @author Parham Ahmady
 * @since 5/31/2022
 */
public class ServerBootstrapper implements Bootstrapper {

    private static final Logger LOGGER = Logger.getLogger(ServerBootstrapper.class.getName());

    private final String applicationName;
    private ApplicationContext applicationContext;

    public ServerBootstrapper(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void initialize(BootstrapProperties bootProperties) {
        LOGGER.info("Starting to Boot {} application", applicationName);
        applicationContext = ApplicationContext.getInstance();
        ClassPathLoader classPathLoader = new ClassPathLoader();
        loadProperties(applicationContext, bootProperties, classPathLoader);
        loadApplicationBeans();
        loadServerInformation();
        LOGGER.info("Boot initialization complete");
    }

    private void loadServerInformation() {
        ObjectIOHelper ioHelper = (ObjectIOHelper) applicationContext.getApplicationBeans().get(ObjectIOHelper.class);
        ServerInformation serverInformation = (ServerInformation) ioHelper.loadObject("INFO" + File.separator + "serverInformation.bin");
        if (serverInformation != null) {
            LOGGER.info("ServerInformation loaded successfully");
            applicationContext.getApplicationBeans().put(ServerInformation.class, serverInformation);
        } else {
            LOGGER.warn("No Saved ServerInformation");
            applicationContext.getApplicationBeans().put(ServerInformation.class, new ServerInformation());
        }
    }

    private void loadApplicationBeans() {
        applicationContext.addBean(ApplicationServer.class, new ApplicationServer());
        applicationContext.addBean(RootDirectoryIOHelper.class, new RootDirectoryIOHelper(applicationContext));
        applicationContext.addBean(ObjectIOHelper.class, new ObjectIOHelper(applicationContext));
        applicationContext.addBean(ChatManagementServiceImpl.class, new ChatManagementServiceImpl());
        applicationContext.addBean(ServerManagementServiceImpl.class, new ServerManagementServiceImpl());
        applicationContext.addBean(UserManagementServiceImpl.class, new UserManagementServiceImpl());
        applicationContext.addBean(AuthenticationServiceImpl.class, new AuthenticationServiceImpl());
        applicationContext.addBean(AuthenticationServiceWrapperImpl.class, new AuthenticationServiceWrapperImpl());
        applicationContext.addBean(ServerManagementServiceWrapperImpl.class, new ServerManagementServiceWrapperImpl());
        applicationContext.addBean(UserManagementServiceWrapperImpl.class, new UserManagementServiceWrapperImpl());
        applicationContext.addBean(ChatManagementServiceWrapperImpl.class, new ChatManagementServiceWrapperImpl());
        LOGGER.info("Application Beans loaded successfully");
    }
}
