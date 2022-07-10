package org.ce.ap.discord.common.entity.business.enumeration;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public enum UserStatus implements Serializable {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE"),
    IDLE("IDLE"),
    DO_NOT_DISTURB("DO_NOT_DISTURB"),
    INVISIBLE("INVISIBLE");

    public final String status;

    UserStatus(String name) {
        this.status = name;
    }
}
