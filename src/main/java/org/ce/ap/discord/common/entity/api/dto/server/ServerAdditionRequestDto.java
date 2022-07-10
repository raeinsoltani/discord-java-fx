package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ServerAdditionRequestDto implements Serializable {

    private static final long serialVersionUID = 1171695191218343876L;

    private final String personId;
    private final String name;

    public ServerAdditionRequestDto(String personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPersonId() {
        return personId;
    }
}
