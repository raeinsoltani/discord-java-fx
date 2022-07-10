package org.ce.ap.discord.server.business.discord;

import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;

/**
 * @author Farhad Aman
 * @since 6/27/2022
 */
public interface ChatManagementService {
    void sendFriendRequest(String senderId, String receiverId) throws Exception;

    void acceptFriendRequest(String senderId, String receiverId) throws Exception;

    void rejectFriendRequest(String senderId, String receiverId) throws Exception;

    void block(String senderId, String receiverId) throws Exception;

    void unblock(String senderId, String receiverId) throws Exception;

    void sendPrivateMessage(String senderId, String receiverId, DiscordMessage discordMessage) throws Exception;

    boolean isChatExists(String senderId, String receiverId) throws Exception;

    String getChatID(String senderId, String receiverId) throws Exception;
}
