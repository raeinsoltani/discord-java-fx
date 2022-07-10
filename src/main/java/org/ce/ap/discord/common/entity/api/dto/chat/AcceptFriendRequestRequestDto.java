package org.ce.ap.discord.common.entity.api.dto.chat;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class AcceptFriendRequestRequestDto implements Serializable {

    private static final long serialVersionUID = 6214192305986893083L;

    private final String senderId;
    private final String receiverId;

    public AcceptFriendRequestRequestDto(String senderId, String receiverId) {
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
