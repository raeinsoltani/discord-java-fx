package org.ce.ap.discord.client.business.network.impl;

import org.ce.ap.discord.client.business.network.ClientNetworkServiceManagement;
import org.ce.ap.discord.client.business.network.DiscordClient;
import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.GeneralDto;
import org.ce.ap.discord.common.entity.api.dto.authentication.*;
import org.ce.ap.discord.common.entity.api.dto.chat.*;
import org.ce.ap.discord.common.entity.api.dto.server.*;
import org.ce.ap.discord.common.entity.api.dto.user.*;
import org.ce.ap.discord.common.entity.api.enumeration.HeaderType;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.*;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Parham Ahmady, Farhad Aman, Arian Qazvini
 * @since 6/27/2022
 */
@SuppressWarnings("unchecked")
public class ClientNetworkServiceManagementImpl implements ClientNetworkServiceManagement, InitializingBean {
    private DiscordClient discordClient;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        discordClient = (DiscordClient) context.getApplicationBeans().get(DiscordClient.class);
    }

    public void initialize() throws IOException {
//        discordClient.initialize();
    }
    @Override
    public Object react(DiscordChannel channel, String messageId, Reaction reaction){
        ReactRequestDto requestDto=new ReactRequestDto(channel,messageId,reaction);
        GeneralDto<ReactRequestDto> dto = new GeneralDto<>(HeaderType.REACT_REQUEST, requestDto);
        GeneralDto<ReactResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().isSuccessful();
        return null;

    }
    @Override
    public Object sendChannelMessages(String serverId, String categoryId, String channelId, DiscordMessage message){
        SendChannelMessageRequestDto requestDto=new SendChannelMessageRequestDto(serverId,channelId,categoryId,message);
        GeneralDto<SendChannelMessageRequestDto> dto = new GeneralDto<>(HeaderType.SEND_CHANNEL_MSG_REQUEST, requestDto);
        GeneralDto<SendChannelMessageResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().isSuccessful();
        return null;
    }
    @Override
    public Object renameServer(String serverId, String name){
        RenameServerRequestDto requestDto=new RenameServerRequestDto(serverId,name);
        GeneralDto<RenameServerRequestDto> dto = new GeneralDto<>(HeaderType.RENAME_SERVER_REQUEST, requestDto);
        GeneralDto<RenameServerResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().isSuccessful();
        return null;
    }

    @Override
    public DiscordChannel selectChannel(String serverId, String categoryId, String channelId) {
        SelectChannelRequestDto requestDto = new SelectChannelRequestDto(serverId, categoryId, channelId);
        GeneralDto<SelectChannelRequestDto> dto = new GeneralDto<>(HeaderType.SELECT_CHANNEL_REQUEST, requestDto);
        GeneralDto<SelectChannelResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().getChannel();
        return null;
    }

    @Override
    public DiscordServer selectServer(String serverId){
        SelectServerRequestDto requestDto=new SelectServerRequestDto(serverId);
        GeneralDto<SelectServerRequestDto> dto = new GeneralDto<>(HeaderType.SELECT_SERVER_REQUEST, requestDto);
        GeneralDto<SelectServerResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().getServer();
        return null;
    }

    @Override
    public Object addChannel(String personId, String serverId, String categoryId, String name, ChannelType type) {
        ChannelAdditionRequestDto requestDto = new ChannelAdditionRequestDto(personId, serverId, categoryId, name, type);
        GeneralDto<ChannelAdditionRequestDto> dto = new GeneralDto<>(HeaderType.ADD_CHANNEL_REQUEST, requestDto);
        GeneralDto<ChannelAdditionResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload();
        return null;
    }

    @Override
    public List<Category> showServerChannels(String serverId) {
        GetServerChannelsRequestDto requestDto = new GetServerChannelsRequestDto(serverId);
        GeneralDto<GetServerChannelsRequestDto> dto = new GeneralDto<>(HeaderType.GET_SERVER_CHANNELS_REQUEST, requestDto);
        GeneralDto<GetServerChannelResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().getServerChannelsList();
        return null;
    }

    @Override
    public Object removeChannel(String serverId, String categoryId, String channelId, String personId) {
        RemoveChannelRequestDto requestDto = new RemoveChannelRequestDto(serverId, categoryId, channelId, personId);
        GeneralDto<RemoveChannelRequestDto> dto = new GeneralDto<>(HeaderType.REMOVE_CHANNEL_REQUEST, requestDto);
        GeneralDto<RemoveChannelResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload();
        return null;

    }

    @Override
    public synchronized void exit() {
        GeneralDto<?> dto = new GeneralDto<>(HeaderType.CLIENT_SHUTDOWN, null);
        discordClient.sendMessage(dto);
    }

    @Override
    public synchronized boolean logOut(Person person) {
        LogoutRequestDto logoutRequest = new LogoutRequestDto(person.getId());
        GeneralDto<LogoutRequestDto> dto = new GeneralDto<>(HeaderType.LOG_OUT_REQUEST, logoutRequest);
        GeneralDto<LogoutResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().isSuccessful();
        return false;
    }

    @Override
    public List<Person> showServerMembers(String serverId) {
        GetServerMembersRequestDto requestDto = new GetServerMembersRequestDto(serverId);
        GeneralDto<GetServerMembersRequestDto> dto = new GeneralDto<>(HeaderType.GET_SERVER_MEMBERS_REQUEST, requestDto);
        GeneralDto<GetServerMembersResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().getServerMembersList();
        return null;
    }

    @Override
    public Object addServerMember(String serverId, String doerId, String personId) {
        PersonAdditionRequestDto requestDto = new PersonAdditionRequestDto(serverId, doerId, personId);
        GeneralDto<PersonAdditionRequestDto> dto = new GeneralDto<>(HeaderType.ADD_PERSON_REQUEST, requestDto);
        GeneralDto<PersonAdditionResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto.getPayload().isSuccessful())
            return null;
        return generalDto.getPayload();
    }

    @Override
    public Object removeServerMember(String serverId, String doerId, String personId) {
        RemoveServerMemberRequestDto requestDto = new RemoveServerMemberRequestDto(doerId, personId, serverId);
        GeneralDto<RemoveServerMemberRequestDto> dto = new GeneralDto<>(HeaderType.REMOVE_SERVER_MEMBER_REQUEST, requestDto);
        GeneralDto<RemoveServerMemberResponseDto> generalDto = discordClient.sendMessage(dto);
        return generalDto.getPayload();
    }

    @Override
    public Object register(String name, String id, String pwd, String email, String phoneNumber) {
        RegisterRequestDto registerRequest = new RegisterRequestDto(name, id, pwd, email, phoneNumber, null);
        GeneralDto<RegisterRequestDto> dto = new GeneralDto<>(HeaderType.REGISTER_REQUEST, registerRequest);
        GeneralDto<RegisterResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailCauseMessage();
        return generalDto.getPayload().getPerson();
    }

    @Override
    public Object showMessages() {
        return null;
    }

    @Override
    public Object getFriendList(String id) {
        GetFriendListRequestDto getFriendListRequestDto = new GetFriendListRequestDto(id);
        GeneralDto<GetFriendListRequestDto> dto = new GeneralDto<>(HeaderType.GET_FRIEND_LIST_REQUEST, getFriendListRequestDto);
        GeneralDto<GetFriendListResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return generalDto.getPayload().getFriendList();
        }
        return null;
    }

    @Override
    public Object getBlockList(String id) {
        GetBlockListRequestDto getBlockListRequestDto = new GetBlockListRequestDto(id);
        GeneralDto<GetBlockListRequestDto> dto = new GeneralDto<>(HeaderType.GET_BLOCK_LIST_REQUEST, getBlockListRequestDto);
        GeneralDto<GetBlockListResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return generalDto.getPayload().getIds();
        }
        return null;
    }

    @Override
    public Object getFriendsRequestsList(String id) {
        GetFriendRequestsRequestDto getFriendRequestsRequestDto = new GetFriendRequestsRequestDto(id);
        GeneralDto<GetFriendRequestsRequestDto> dto = new GeneralDto<>(HeaderType.GET_FRIEND_REQUESTS_REQUEST, getFriendRequestsRequestDto);
        GeneralDto<GetFriendRequestsResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return generalDto.getPayload().getIds();
        }
        return null;
    }

    @Override
    public Object getPrivateChats(String id) {
        GetPrivateChatsRequestDto getPrivateChatsRequestDto = new GetPrivateChatsRequestDto(id);
        GeneralDto<GetPrivateChatsRequestDto> dto = new GeneralDto<>(HeaderType.GET_CHATS_REQUEST, getPrivateChatsRequestDto);
        GeneralDto<GetPrivateChatsResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return generalDto.getPayload().getChats();
        }
        return null;
    }

    @Override
    public Object getServersList(String id) {
        GetServersListRequestDto getServersListRequestDto = new GetServersListRequestDto(id);
        GeneralDto<GetServersListRequestDto> dto = new GeneralDto<>(HeaderType.GET_SERVERS_LIST_REQUEST, getServersListRequestDto);
        GeneralDto<GetServersListResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return generalDto.getPayload().getServerIds();
        }
        return null;

    }

    @Override
    public boolean changePhoto(String id, byte[] newPhoto) {
        ChangePhotoRequestDto changePhotoRequestDto = new ChangePhotoRequestDto(id, newPhoto);
        GeneralDto<ChangePhotoRequestDto> dto = new GeneralDto<>(HeaderType.CHANGE_IMG_REQUEST, changePhotoRequestDto);
        GeneralDto<ChangePhotoResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String id, String newPassword) {
        ChangePasswordRequestdto changePasswordRequestdto = new ChangePasswordRequestdto(id, newPassword);
        GeneralDto<ChangePasswordRequestdto> dto = new GeneralDto<>(HeaderType.CHANGE_PWD_REQUEST, changePasswordRequestdto);
        GeneralDto<ChangePasswordResponseDto> generalDto = discordClient.sendMessage(dto);
        if (generalDto != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object sendTextMessage(Person sender, String senderId, String receiverId, String message) {
        SendPrivateMessageRequestDto sendPrivateMessageRequestDto = new SendPrivateMessageRequestDto(senderId, receiverId, new DiscordMessage<String>(sender, message));
        GeneralDto<SendPrivateMessageRequestDto> dto = new GeneralDto<>(HeaderType.SEND_PRIVATE_MESSAGE_REQUEST, sendPrivateMessageRequestDto);
        GeneralDto<SendPrivateMessageResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    @Override
    public Object sendFileMessage(Person sender, String senderId, String receiverId, byte[] bytes, String fileFormat) {
        SendPrivateMessageRequestDto sendPrivateMessageRequestDto = new SendPrivateMessageRequestDto
                (senderId, receiverId, new DiscordMessage<FileMessage>(sender, new FileMessage(bytes, fileFormat)));
        GeneralDto<SendPrivateMessageRequestDto> dto = new GeneralDto<>(HeaderType.SEND_PRIVATE_MESSAGE_REQUEST, sendPrivateMessageRequestDto);
        GeneralDto<SendPrivateMessageResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    @Override
    public Object sendFriendRequest(String senderId, String receiverId) {
        SendFriendRequestRequestDto sendFriendRequestRequestDto = new SendFriendRequestRequestDto(senderId, receiverId);
        GeneralDto<SendFriendRequestRequestDto> dto = new GeneralDto<>(HeaderType.SEND_FRIEND_REQUEST_REQUEST, sendFriendRequestRequestDto);
        GeneralDto<SendFriendRequestResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    @Override
    public Object acceptFriendRequest(String senderId, String receiverId) {
        AcceptFriendRequestRequestDto acceptFriendRequestRequestDto = new AcceptFriendRequestRequestDto(senderId, receiverId);
        GeneralDto<AcceptFriendRequestRequestDto> dto = new GeneralDto<>(HeaderType.ACCEPT_FRIEND_REQUEST_REQUEST, acceptFriendRequestRequestDto);
        GeneralDto<AcceptFriendRequestResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    @Override
    public Object rejectFriendRequest(String senderId, String receiverId) {
        RejectFriendRequestRequestDto rejectFriendRequestRequestDto = new RejectFriendRequestRequestDto(senderId, receiverId);
        GeneralDto<RejectFriendRequestRequestDto> dto = new GeneralDto<>(HeaderType.REJECT_FRIEND_REQUEST_REQUEST, rejectFriendRequestRequestDto);
        GeneralDto<RejectFriendRequestResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    public Object login(String id, String pwd) {
        LoginRequestDto loginRequest = new LoginRequestDto(id, pwd);
        GeneralDto<LoginRequestDto> dto = new GeneralDto<>(HeaderType.LOG_IN_REQUEST, loginRequest);
        GeneralDto<LoginResponseDto> loginResponseDtoGeneralDto = discordClient.sendMessage(dto);
        if (!loginResponseDtoGeneralDto.getPayload().isSuccessful()) {
            return loginResponseDtoGeneralDto.getPayload().getMessage();
        }
        return loginResponseDtoGeneralDto.getPayload().getPerson();
    }

    @Override
    public Object block(String senderId, String receiverId) {
        BlockRequestDto blockRequestDto = new BlockRequestDto(senderId, receiverId);
        GeneralDto<BlockRequestDto> dto = new GeneralDto<>(HeaderType.BLOCK_REQUEST, blockRequestDto);
        GeneralDto<BlockResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    @Override
    public Object unblock(String senderId, String receiverId) {
        UnblockRequestDto unblockRequestDto = new UnblockRequestDto(senderId, receiverId);
        GeneralDto<UnblockRequestDto> dto = new GeneralDto<>(HeaderType.UNBLOCK_REQUEST, unblockRequestDto);
        GeneralDto<UnblockResponseDto> generalDto = discordClient.sendMessage(dto);
        if (!generalDto.getPayload().isSuccessful())
            return generalDto.getPayload().getFailureCause();
        return null;
    }

    @Override
    public List<DiscordMessage> showMessages(String getterId, String contactId) {
        GetPrivateChatsRequestDto getPrivateChatsRequestDto = new GetPrivateChatsRequestDto(getterId);
        GeneralDto<GetPrivateChatsRequestDto> dto = new GeneralDto<>(HeaderType.GET_CHATS_REQUEST, getPrivateChatsRequestDto);
        GeneralDto<GetPrivateChatsResponseDto> generalDto = discordClient.sendMessage(dto);
        List<DiscordMessage> messages = new ArrayList<>();
        if (generalDto.getPayload().getChats() != null) {
            for (PrivateChat chat : generalDto.getPayload().getChats()) {
                if ((chat.getPerson1().getId().equals(contactId) && chat.getPerson2().getId().equals(getterId)) || (chat.getPerson1().getId().equals(getterId) && chat.getPerson2().getId().equals(contactId))) {
                    messages.addAll(chat.getMessages());
                }
            }
        }
        return messages;
    }

    @Override
    public boolean addServer(String personId, String name) {
        ServerAdditionRequestDto serverAdditionRequestDto = new ServerAdditionRequestDto(personId, name);
        GeneralDto<ServerAdditionRequestDto> dto = new GeneralDto<>(HeaderType.ADD_SERVER_REQUEST, serverAdditionRequestDto);
        GeneralDto<ServerAdditionResponseDto> response = discordClient.sendMessage(dto);
        return response.getPayload().isSuccessful();
    }

    @Override
    public Person updatePerson(String personId) {
        GeneralDto<UpdatePersonRequestDto> dto = new GeneralDto<>(HeaderType.UPDATE_PERSON_REQUEST, new UpdatePersonRequestDto(personId));
        GeneralDto<UpdatePersonResponseDto> response = discordClient.sendMessage(dto);
        if (response != null)
            return response.getPayload().getPerson();
        return null;
    }
}
