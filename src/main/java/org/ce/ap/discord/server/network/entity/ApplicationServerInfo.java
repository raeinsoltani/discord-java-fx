package org.ce.ap.discord.server.network.entity;

import org.ce.ap.discord.server.network.ClientHandler;
import org.ce.ap.discord.server.network.entity.enumeration.ServerStatus;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Parham Ahmady
 * @since 5/31/2022
 */
public class ApplicationServerInfo {
    private final Set<ClientHandler> clientHandlers;
    private int serverPort;
    private int maxConnection;
    private int aliveConnectionCount;
    private ServerStatus serverStatus;

    public ApplicationServerInfo() {
        clientHandlers = new HashSet<>();
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Set<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public void addClientHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public int getAliveConnectionCount() {
        return aliveConnectionCount;
    }

    public void increaseAliveConnectionCount() {
        this.aliveConnectionCount++;
    }

    public void decreaseAliveConnectionCount() {
        this.aliveConnectionCount--;
    }
}
