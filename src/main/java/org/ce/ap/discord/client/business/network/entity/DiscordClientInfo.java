package org.ce.ap.discord.client.business.network.entity;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public class DiscordClientInfo {
    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
