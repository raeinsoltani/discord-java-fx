package org.ce.ap.discord.server.network.entity.exception;

/**
 * @author Parham Ahmady
 * @since 6/1/2022
 */
public class ClientHandlerInitializationException extends Exception {

    private final String socketInfo;

    public ClientHandlerInitializationException(String message, String socketInfo) {
        super(message);
        this.socketInfo = socketInfo;
    }

    public String getSocketInfo() {
        return socketInfo;
    }
}
