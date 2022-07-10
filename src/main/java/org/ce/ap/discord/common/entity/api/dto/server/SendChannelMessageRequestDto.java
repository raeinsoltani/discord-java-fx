package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;

public class SendChannelMessageRequestDto {
    private final String serverId;
    private final String channelId;
    private final String categoryId;
    private final DiscordMessage message;

    public SendChannelMessageRequestDto(String serverId, String channelId, String categoryId, DiscordMessage message) {
        this.serverId = serverId;
        this.channelId = channelId;
        this.categoryId = categoryId;
        this.message = message;
    }

    public String getServerId() {
        return serverId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public DiscordMessage getMessage() {
        return message;
    }
}
