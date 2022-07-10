package org.ce.ap.discord.client.business.network;

import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.Category;
import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;
import org.ce.ap.discord.common.entity.business.discord.DiscordServer;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;

import java.io.IOException;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public interface ClientNetworkServiceManagement {

    Object removeChannel(String serverId, String categoryId, String channelId, String personId);

    void exit();

    boolean logOut(Person person);

    Object register(String name, String id, String pwd, String email, String phoneNumber);

    Object login(String id, String pwd);

    Object showMessages();

    List<DiscordMessage> showMessages(String getterId, String contactId);

    Object getFriendList(String id);

    Object getBlockList(String id);

    Object getFriendsRequestsList(String id);

    Object getPrivateChats(String id);

    Object getServersList(String id);

    Object react(DiscordChannel channel, String messageId, Reaction reaction);

    Object sendChannelMessages(String serverId, String categoryId, String channelId, DiscordMessage message);

    Object renameServer(String serverId, String name);

    DiscordChannel selectChannel(String serverId, String categoryId, String channelId);

    boolean changePhoto(String id ,  byte[] newPhoto);
    boolean changePassword(String Id,String newPassword);

    Object sendTextMessage(Person sender, String senderId, String receiverId, String message);

    Object addServerMember(String serverId, String doerId, String personId);

    Object removeServerMember(String serverId, String doerId, String personId);

    List<Person> showServerMembers(String serverId);

    Object sendFileMessage(Person sender, String senderId, String receiverId, byte[] bytes, String fileFormat);

    Object sendFriendRequest(String senderId, String receiverId);

    Object acceptFriendRequest(String senderId, String receiverId);

    Object rejectFriendRequest(String senderId, String receiverId);

    Object block(String senderId, String receiverId);

    Object unblock(String senderId, String receiverId);

    List<Category> showServerChannels(String serverId);

    DiscordServer selectServer(String serverId);

    Object addChannel(String personId, String serverId, String categoryId, String name, ChannelType type);

    default void initialize() throws IOException {
    }

    boolean addServer(String personId, String name);

    Person updatePerson(String personId);
}
