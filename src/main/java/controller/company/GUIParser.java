package controller.company;

import org.ce.ap.discord.client.business.display.DisplayService;
import org.ce.ap.discord.client.business.display.impl.DisplayServiceImpl;
import org.ce.ap.discord.client.business.network.ClientNetworkServiceManagement;
import org.ce.ap.discord.client.business.network.impl.ClientNetworkServiceManagementImpl;
import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.util.logger.Logger;

import java.io.IOException;

public class GUIParser implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(GUIParser.class.getName());

    public static ClientNetworkServiceManagement networkService;

    public static DisplayService displayService;

    public static Person loginUser;


    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        networkService = (ClientNetworkServiceManagement) context.getApplicationBeans().get(ClientNetworkServiceManagementImpl.class);
        displayService = (DisplayService) context.getApplicationBeans().get(DisplayServiceImpl.class);
    }

    public void start() throws IOException {
        LOGGER.info("GUI Parser Starting");
        networkService.initialize();
    }

}
