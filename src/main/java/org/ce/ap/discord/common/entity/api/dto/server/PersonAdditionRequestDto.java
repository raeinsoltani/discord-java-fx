package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class PersonAdditionRequestDto implements Serializable {

    private static final long serialVersionUID = 1588974846140459305L;

    private final String serverId;
    private final String doerId;
    private final String personId;

    public PersonAdditionRequestDto(String serverId, String doerId, String personId) {
        this.serverId = serverId;
        this.doerId = doerId;
        this.personId = personId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getDoerId() {
        return doerId;
    }

    public String getPersonId() {
        return personId;
    }
}
