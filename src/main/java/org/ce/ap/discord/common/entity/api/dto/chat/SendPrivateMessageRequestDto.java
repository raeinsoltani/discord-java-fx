package org.ce.ap.discord.common.entity.api.dto.chat;

import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class SendPrivateMessageRequestDto implements Serializable {

    private static final long serialVersionUID = 8589202909789837743L;

    private final String senderId;
    private final String receiverId;
    private final DiscordMessage discordMessage;

    public SendPrivateMessageRequestDto(String senderId, String receiverId, DiscordMessage discordMessage) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.discordMessage = discordMessage;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public DiscordMessage getMessage() {
        return discordMessage;
    }
}
