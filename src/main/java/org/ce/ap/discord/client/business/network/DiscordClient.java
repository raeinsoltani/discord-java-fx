package org.ce.ap.discord.client.business.network;

import org.ce.ap.discord.client.business.network.entity.DiscordClientInfo;
import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.GeneralDto;
import org.ce.ap.discord.common.entity.api.enumeration.HeaderType;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.util.logger.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public class DiscordClient implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(DiscordClient.class.getName());

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DiscordClientInfo clientInfo;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        Properties applicationProperties = context.getApplicationProperties();
        this.clientInfo = makeClientInfo(applicationProperties);
    }

    public void initialize() throws IOException {
        try {
            LOGGER.info("Connect to Server: Host: {} , Port: {}", clientInfo.getHost(), String.valueOf(clientInfo.getPort()));
            socket = new Socket(clientInfo.getHost(), clientInfo.getPort());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.error("Cant Connect to Server: Host: {} , Port: {}", clientInfo.getHost(), String.valueOf(clientInfo.getPort()));
            throw e;
        }
    }

    private void shutdown() {
        LOGGER.info("Starting to close connection");
        GeneralDto<Object> message = new GeneralDto<>(HeaderType.CLIENT_SHUTDOWN, null);
        try {
            oos.writeObject(message);
            LOGGER.info("Connection closed successfully");
            Thread.sleep(100);
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            LOGGER.warn("Error while closing connection");
        }
    }

    private DiscordClientInfo makeClientInfo(Properties applicationProperties) {
        DiscordClientInfo clientInfo = new DiscordClientInfo();
        clientInfo.setPort(Integer.parseInt(String.valueOf(applicationProperties.get("server.port"))));
        clientInfo.setHost(String.valueOf(applicationProperties.get("server.host")));
        return clientInfo;
    }

    @SuppressWarnings("rawtypes")
    public synchronized GeneralDto sendMessage(GeneralDto message) {
        if (socket == null || (socket.isClosed())) {
            try {
                initialize();
            } catch (IOException e) {
                LOGGER.error("Error while sending message");
            }
        }
        GeneralDto result = sending(message);
        if (message.getHeader() != HeaderType.CLIENT_SHUTDOWN) {
            GeneralDto<?> dto = new GeneralDto<>(HeaderType.CLIENT_SHUTDOWN, null);
            sending(dto);
            try {
                socket.close();
            } catch (IOException ignored) {

            }
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    private GeneralDto sending(GeneralDto message) {
        try {
            oos.writeObject(message);
            Thread.sleep(100);
            GeneralDto response = (GeneralDto) ois.readObject();
            if (response.getHeader().equals(HeaderType.ERROR))
                return null;
            return response;
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            if (message.getHeader() != HeaderType.CLIENT_SHUTDOWN)
                LOGGER.error("Cant Send Message: {} , error: {}", message.toString(), e.getMessage());
            return null;
        }
    }
}
