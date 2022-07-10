package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class CategoryAdditionRequestDto implements Serializable {

    private static final long serialVersionUID = 4463563910629402653L;

    private final String serverId;
    private final String name;
    private final String personId;

    public CategoryAdditionRequestDto(String serverId, String name, String personId) {
        this.serverId = serverId;
        this.name = name;
        this.personId = personId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getName() {
        return name;
    }

    public String getPersonId() {
        return personId;
    }
}
