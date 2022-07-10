package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ChannelAdditionRequestDto implements Serializable {

    private static final long serialVersionUID = 1307699566597991725L;

    private final String personId;
    private final String serverId;
    private final String categoryId;
    private final String name;
    private final ChannelType type;

    public ChannelAdditionRequestDto(String personId, String serverId, String categoryId, String name, ChannelType type) {
        this.personId = personId;
        this.serverId = serverId;
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPersonId() {
        return personId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public ChannelType getType() {
        return type;
    }
}
