package org.ce.ap.discord.server.network.entity;

import org.ce.ap.discord.server.network.entity.enumeration.ClientHandlerStatusType;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public class ClientHandlerStatus {
    private ClientHandlerStatusType status;

    public ClientHandlerStatusType getStatus() {
        return status;
    }

    public void setStatus(ClientHandlerStatusType status) {
        this.status = status;
    }
}
