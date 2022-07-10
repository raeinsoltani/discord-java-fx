package org.ce.ap.discord.server.network.entity.enumeration;

/**
 * @author Parham Ahmady
 * @since 5/31/2022
 */
public enum ServerStatus {
    RUNNING("RUNNING"),
    DISABLE("DISABLE"),
    SHUTDOWN("SHUTDOWN"),
    RUNNING_AT_MAX("RUNNING_AT_MAX");

    public final String status;

    ServerStatus(String status) {
        this.status = status;
    }
}
