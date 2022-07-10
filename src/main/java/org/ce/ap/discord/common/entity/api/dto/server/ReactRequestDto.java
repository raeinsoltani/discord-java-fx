package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;

public class ReactRequestDto {
    private final DiscordChannel channel;
    private final String messageId;
    private final Reaction reaction;

    public ReactRequestDto(DiscordChannel channel, String messageId, Reaction reaction) {
        this.channel = channel;
        this.messageId = messageId;
        this.reaction = reaction;
    }

    public DiscordChannel getChannel() {
        return channel;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public String getMessageId() {
        return messageId;
    }
}
