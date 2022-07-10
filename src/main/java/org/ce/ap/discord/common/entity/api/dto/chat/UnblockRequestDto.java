package org.ce.ap.discord.common.entity.api.dto.chat;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class UnblockRequestDto implements Serializable {

    private static final long serialVersionUID = 2031700827244882130L;

    private final String senderId;
    private final String receiverId;

    public UnblockRequestDto(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
