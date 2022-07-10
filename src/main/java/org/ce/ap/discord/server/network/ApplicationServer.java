package org.ce.ap.discord.server.network;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.util.logger.Logger;
import org.ce.ap.discord.server.network.entity.ApplicationServerInfo;
import org.ce.ap.discord.server.network.entity.enumeration.ClientHandlerStatusType;
import org.ce.ap.discord.server.network.entity.enumeration.ServerStatus;
import org.ce.ap.discord.server.network.entity.exception.ClientHandlerInitializationException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Parham Ahmady
 * @since 5/31/2022
 */
public class ApplicationServer implements Runnable, PropertyChangeListener, InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(ApplicationServer.class.getName());

    private final PropertyChangeSupport observable;
    private ApplicationServerInfo serverInfo;
    private ServerSocket serverSocket;
    private ExecutorService handlerPool;

    public ApplicationServer() {
        observable = new PropertyChangeSupport(this);
    }

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        Properties applicationProperties = context.getApplicationProperties();
        serverInfo = makeServerInfo(applicationProperties);
    }

    public void initialize() throws IOException {
        LOGGER.info("Starting to initialize Discord Server");
        try {
            this.serverSocket = new ServerSocket(serverInfo.getServerPort());
        } catch (IOException e) {
            LOGGER.error("Cant Initialize Server at Port {}", Integer.toString(serverInfo.getServerPort()));
            throw e;
        }
        this.handlerPool = Executors.newCachedThreadPool();
        serverInfo.setServerStatus(ServerStatus.RUNNING);
        LOGGER.info("Discord Server is Up at port {}", Integer.toString(serverInfo.getServerPort()));
    }

    @Override
    public void run() {
        while (serverInfo.getServerStatus().equals(ServerStatus.RUNNING)) {
            if (serverInfo.getAliveConnectionCount() >= serverInfo.getMaxConnection()) {
                LOGGER.warn("Discord Server reach its Maximum Connection");
                serverInfo.setServerStatus(ServerStatus.RUNNING_AT_MAX);
                break;
            }
            LOGGER.info("Server has {} aliveConnections", Integer.toString(serverInfo.getAliveConnectionCount()));

            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler unusedHandler = findUnusedHandler();
                if (unusedHandler == null) {
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    clientHandler.afterPropertiesSet(ApplicationContext.getInstance());
                    serverInfo.addClientHandler(clientHandler);
                    serverInfo.increaseAliveConnectionCount();
                    observable.addPropertyChangeListener(clientHandler);
                    handlerPool.execute(clientHandler);
                } else {
                    unusedHandler.reload();
                    unusedHandler.initialize(clientSocket);
                    serverInfo.increaseAliveConnectionCount();
                    handlerPool.execute(unusedHandler);
                }
            } catch (IOException e) {
                LOGGER.warn("Can't connect to client, error Message is {}", e.toString());
            } catch (ClientHandlerInitializationException e) {
                LOGGER.warn("Can't initialize client Handler for {}", e.getSocketInfo());
            }
        }
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent event) {
        ClientHandler clientHandler = (ClientHandler) event.getNewValue();
        clientHandler.setHandlerStatus(ClientHandlerStatusType.DISABLE);
        serverInfo.decreaseAliveConnectionCount();
        observable.removePropertyChangeListener(clientHandler);
        if (serverInfo.getServerStatus().equals(ServerStatus.RUNNING_AT_MAX)) {
            serverInfo.setServerStatus(ServerStatus.RUNNING);
            run();
        }
        LOGGER.info("Server has {} alive connections", String.valueOf(serverInfo.getAliveConnectionCount()));
    }

    @SuppressWarnings("BusyWait")
    public synchronized void shutdown() throws InterruptedException {
        LOGGER.info("Starting shutdown Server");
        serverInfo.setServerStatus(ServerStatus.SHUTDOWN);
        observable.firePropertyChange("ApplicationServerInformation", null, serverInfo);
        while (serverInfo.getAliveConnectionCount() > 0) {
            Thread.sleep(1000);
        }
        try {
            serverSocket.close();
            LOGGER.info("Server shutdown successfully");
        } catch (IOException e) {
            LOGGER.error("Cant stop Server socket {}", serverSocket.toString());
        }
    }

    private ApplicationServerInfo makeServerInfo(Properties applicationProperties) {
        final ApplicationServerInfo serverInfo;
        serverInfo = new ApplicationServerInfo();
        serverInfo.setServerPort(Integer.parseInt((String) applicationProperties.get("server.port")));
        serverInfo.setMaxConnection(Integer.parseInt((String) applicationProperties.get("server.max.connections")));
        serverInfo.setServerStatus(ServerStatus.DISABLE);
        return serverInfo;
    }


    private ClientHandler findUnusedHandler() {
        for (ClientHandler clientHandler : serverInfo.getClientHandlers()) {
            if (clientHandler.getHandlerStatus().getStatus().equals(ClientHandlerStatusType.DISABLE))
                return clientHandler;
        }
        return null;
    }
}
