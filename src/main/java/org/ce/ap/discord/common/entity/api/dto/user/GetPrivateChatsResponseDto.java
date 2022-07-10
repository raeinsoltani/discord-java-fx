package org.ce.ap.discord.common.entity.api.dto.user;

import org.ce.ap.discord.common.entity.business.discord.PrivateChat;

import java.io.Serializable;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetPrivateChatsResponseDto implements Serializable {

    private static final long serialVersionUID = -5206200609741200962L;

    private final List<PrivateChat> chats;

    public GetPrivateChatsResponseDto(List<PrivateChat> chats) {
        this.chats = chats;
    }

    public List<PrivateChat> getChats() {
        return chats;
    }
}
