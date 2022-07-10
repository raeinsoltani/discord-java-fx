package org.ce.ap.discord.client.business.network.entity.enumeration;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public enum ClientStatus {
    UP("UP"),
    DOWN("DOWN");

    public final String status;

    ClientStatus(String status) {
        this.status = status;
    }
}
