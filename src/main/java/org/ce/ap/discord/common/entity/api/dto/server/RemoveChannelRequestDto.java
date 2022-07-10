package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class RemoveChannelRequestDto implements Serializable {

    private static final long serialVersionUID = 3728026662677365373L;

    private final String serverId;
    private final String categoryId;
    private final String channelId;
    private final String personId;

    public RemoveChannelRequestDto(String serverId, String categoryId, String channelId, String personId) {
        this.serverId = serverId;
        this.categoryId = categoryId;
        this.channelId = channelId;
        this.personId = personId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getPersonId() {
        return personId;
    }
}
