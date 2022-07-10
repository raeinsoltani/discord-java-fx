package org.ce.ap.discord.server.business.discord.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.ServerInformation;
import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;
import org.ce.ap.discord.server.business.discord.ChatManagementService;
import org.ce.ap.discord.server.business.discord.exceptions.*;

/**
 * @author Farhad Aman
 * @since 6/27/2022
 */
public class ChatManagementServiceImpl implements ChatManagementService, InitializingBean {
    private ServerInformation serverInformation;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        serverInformation = (ServerInformation) context.getApplicationBeans().get(ServerInformation.class);
    }

    @Override
    public void sendFriendRequest(String senderId, String receiverId) throws Exception {
        if (senderId.equals(receiverId)) {
            throw new InvalidChatException("Cant send friend request to yourself");
        } else if (isChatExists(senderId, receiverId)) {
            throw new ChatExistsException("Chat already exists");
        } else {
            if (serverInformation.getPersons().containsKey(receiverId)) {
                serverInformation.getPersons().get(receiverId).addFriendRequest(senderId);
            } else {
                throw new ReceiverNotFoundException("Receiver not found");
            }
        }

    }

    @Override
    public void acceptFriendRequest(String senderId, String receiverId) throws Exception {
        if (isChatExists(senderId, receiverId)) {
            throw new ChatExistsException("Chat already exists");
        } else {
            if (serverInformation.getPersons().containsKey(receiverId)) {
                if (serverInformation.getPersons().get(receiverId).getFriendRequests().contains(senderId)) {
                    serverInformation.getPersons().get(receiverId).removeFriendRequest(senderId);
                    serverInformation.getChats().put(getChatID(senderId, receiverId), new PrivateChat(serverInformation.getPersons().get(senderId), serverInformation.getPersons().get(receiverId)));
                } else {
                    throw new FriendRequestNotFoundException("Friend request not found");
                }
            } else {
                throw new ReceiverNotFoundException("Receiver not found");
            }
        }
    }

    @Override
    public void rejectFriendRequest(String senderId, String receiverId) throws Exception {
        if (serverInformation.getPersons().containsKey(receiverId)) {
            if (serverInformation.getPersons().get(receiverId).getFriendRequests().contains(senderId)) {
                serverInformation.getPersons().get(receiverId).removeFriendRequest(senderId);
            } else {
                throw new FriendRequestNotFoundException("Friend request not found");
            }
        } else {
            throw new ReceiverNotFoundException("Receiver not found");
        }
    }

    @Override
    public void block(String senderId, String receiverId) throws Exception {
        if (serverInformation.getPersons().containsKey(receiverId)) {
            if (!serverInformation.getPersons().get(senderId).getBlocked().contains(receiverId)) {
                serverInformation.getPersons().get(senderId).getBlocked().add(receiverId);
            } else {
                throw new AlreadyBlockedException("Receiver is already blocked");
            }
        } else {
            throw new ReceiverNotFoundException("Receiver not found");
        }
    }

    @Override
    public void unblock(String senderId, String receiverId) throws Exception {
        if (serverInformation.getPersons().containsKey(receiverId)) {
            if (serverInformation.getPersons().get(senderId).getBlocked().contains(receiverId)) {
                serverInformation.getPersons().get(senderId).getBlocked().remove(receiverId);
            } else {
                throw new NotBlockedException("Receiver is not blocked");
            }
        } else {
            throw new ReceiverNotFoundException("Receiver not found");
        }
    }

    @Override
    public void sendPrivateMessage(String senderId, String receiverId, DiscordMessage discordMessage) throws Exception {
        if (serverInformation.getPersons().containsKey(receiverId)) {
            if (serverInformation.getPersons().get(receiverId).getBlocked().contains(senderId)) {
                throw new BlockedException("Receiver is blocked");
            } else {
                if (isChatExists(senderId, receiverId)) {
                    serverInformation.getChats().get(getChatID(senderId, receiverId)).getMessages().add(discordMessage);
                } else {
                    throw new ChatNotFoundException("Chat not found");
                }
            }
        } else {
            throw new ReceiverNotFoundException("Receiver not found");
        }
    }

    @Override
    public boolean isChatExists(String senderId, String receiverId) {
        String chatID = getChatID(senderId, receiverId);
        return serverInformation.getChats().containsKey(chatID);
    }

    @Override
    public String getChatID(String senderId, String receiverId) {
        String chatID;
        if (senderId.compareTo(receiverId) < 0) {
            chatID = senderId + "-" + receiverId;
        } else {
            chatID = receiverId + "-" + senderId;
        }
        return chatID;
    }
}
