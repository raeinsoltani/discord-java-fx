package org.ce.ap.discord.server.network.entity.enumeration;

/**
 * @author Parham Ahmady
 * @since 6/1/2022
 */
public enum ClientHandlerStatusType {
    RUNNING("RUNNING"),
    DISABLE("DISABLE"),
    BUSY("BUSY");

    public final String status;

    ClientHandlerStatusType(String status) {
        this.status = status;
    }
}
