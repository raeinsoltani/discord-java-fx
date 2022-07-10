package org.ce.ap.discord.server.network;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.GeneralDto;
import org.ce.ap.discord.common.entity.api.dto.authentication.*;
import org.ce.ap.discord.common.entity.api.dto.chat.*;
import org.ce.ap.discord.common.entity.api.dto.server.*;
import org.ce.ap.discord.common.entity.api.dto.user.*;
import org.ce.ap.discord.common.entity.api.enumeration.HeaderType;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.util.logger.Logger;
import org.ce.ap.discord.server.business.authentication.wrapper.AuthenticationServiceWrapper;
import org.ce.ap.discord.server.business.authentication.wrapper.impl.AuthenticationServiceWrapperImpl;
import org.ce.ap.discord.server.business.discord.wrapper.ChatManagementServiceWrapper;
import org.ce.ap.discord.server.business.discord.wrapper.ServerManagementServiceWrapper;
import org.ce.ap.discord.server.business.discord.wrapper.UserManagementServiceWrapper;
import org.ce.ap.discord.server.business.discord.wrapper.impl.ChatManagementServiceWrapperImpl;
import org.ce.ap.discord.server.business.discord.wrapper.impl.ServerManagementServiceWrapperImpl;
import org.ce.ap.discord.server.business.discord.wrapper.impl.UserManagementServiceWrapperImpl;
import org.ce.ap.discord.server.network.entity.ApplicationServerInfo;
import org.ce.ap.discord.server.network.entity.ClientHandlerStatus;
import org.ce.ap.discord.server.network.entity.enumeration.ClientHandlerStatusType;
import org.ce.ap.discord.server.network.entity.enumeration.ServerStatus;
import org.ce.ap.discord.server.network.entity.exception.ClientHandlerInitializationException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Parham Ahmady
 * @since 5/31/2022
 */
public class ClientHandler implements Runnable, PropertyChangeListener, InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());

    private Socket socket;
    private final PropertyChangeSupport observable;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Thread currentThread;
    private ClientHandlerStatus handlerStatus = new ClientHandlerStatus();
    private AuthenticationServiceWrapper authenticationServiceWrapper;
    private ServerManagementServiceWrapper serverManagementServiceWrapper;
    private UserManagementServiceWrapper userManagementServiceWrapper;
    private ChatManagementServiceWrapper chatManagementServiceWrapper;
    private int maxMessagingError = 5;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        authenticationServiceWrapper = (AuthenticationServiceWrapper) context.getApplicationBeans().get(AuthenticationServiceWrapperImpl.class);
        serverManagementServiceWrapper = (ServerManagementServiceWrapper) context.getApplicationBeans().get(ServerManagementServiceWrapperImpl.class);
        userManagementServiceWrapper = (UserManagementServiceWrapper) context.getApplicationBeans().get(UserManagementServiceWrapperImpl.class);
        chatManagementServiceWrapper = (ChatManagementServiceWrapper) context.getApplicationBeans().get(ChatManagementServiceWrapperImpl.class);
    }

    public ClientHandler(Socket socket, ApplicationServer applicationServer) throws ClientHandlerInitializationException {
        this.observable = new PropertyChangeSupport(this);
        observable.addPropertyChangeListener(applicationServer);
        initialize(socket);
    }

    public void initialize(Socket socket) throws ClientHandlerInitializationException {
        this.socket = socket;
        this.handlerStatus.setStatus(ClientHandlerStatusType.RUNNING);
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                throw new ClientHandlerInitializationException("Cant open OOS ans OIS", socket.toString());
            }
            throw new ClientHandlerInitializationException("Cant open OOS ans OIS", socket.toString());
        }
    }

    public void reload() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            socket = null;
        }
    }

    @Override
    @SuppressWarnings({"rawtypes", "EnhancedSwitchMigration"})
    public void run() {
        currentThread = Thread.currentThread();
        while (handlerStatus.getStatus().equals(ClientHandlerStatusType.RUNNING)) {
            try {
                GeneralDto generalMessage = (GeneralDto) ois.readObject();
                HeaderType headerType = generalMessage.getHeader();
                handlerStatus.setStatus(ClientHandlerStatusType.BUSY);
                switch (headerType) {
                    case CLIENT_SHUTDOWN:
                        handlerStatus.setStatus(ClientHandlerStatusType.DISABLE);
                        shutdown();
                        break;
                    case REACT_REQUEST:
                        ReactResponseDto reactResponseDto=serverManagementServiceWrapper.react((ReactRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.REACT_RESPONSE, reactResponseDto));
                        break;
                    case SEND_CHANNEL_MSG_REQUEST:
                        SendChannelMessageResponseDto sendChannelMessageResponseDto=serverManagementServiceWrapper.sendChannelMessage((SendChannelMessageRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.SEND_CHANNEL_MSG_RESPONSE, sendChannelMessageResponseDto));
                        break;
                    case SELECT_SERVER_REQUEST:
                        SelectServerResponseDto selectServerResponseDto=serverManagementServiceWrapper.selectServer((SelectServerRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.SELECT_SERVER_RESPONSE, selectServerResponseDto));
                        break;
                    case RENAME_SERVER_REQUEST:
                        RenameServerResponseDto renameServerResponseDto=serverManagementServiceWrapper.renameServer((RenameServerRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.RENAME_SERVER_RESPONSE, renameServerResponseDto));
                        break;
                    case SELECT_CHANNEL_REQUEST:
                        SelectChannelResponseDto selectChannelResponseDto = serverManagementServiceWrapper.selectChannel((SelectChannelRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.SELECT_CHANNEL_RESPONSE, selectChannelResponseDto));
                        break;
                    case GET_SERVER_MEMBERS_REQUEST:
                        GetServerMembersResponseDto getServerMembersResponseDto = serverManagementServiceWrapper.getServerMembers((GetServerMembersRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_SERVER_MEMBERS_RESPONSE, getServerMembersResponseDto));
                        break;
                    case GET_SERVER_CHANNELS_REQUEST:
                        GetServerChannelResponseDto getServerChannelResponseDto = serverManagementServiceWrapper.getServerChannels((GetServerChannelsRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_SERVER_CHANNELS_RESPONSE, getServerChannelResponseDto));
                        break;
                    case REMOVE_CHANNEL_REQUEST:
                        RemoveChannelResponseDto rmChannelResponseDto = serverManagementServiceWrapper.removeChannel((RemoveChannelRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.REMOVE_CHANNEL_RESPONSE, rmChannelResponseDto));
                        break;
                    case REGISTER_REQUEST:
                        RegisterResponseDto responseDto = authenticationServiceWrapper.registerUser((RegisterRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.REGISTER_RESPONSE, responseDto));
                        break;
                    case LOG_IN_REQUEST:
                        LoginResponseDto loginResponseDto = authenticationServiceWrapper.logInUser((LoginRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.LOG_IN_RESPONSE, loginResponseDto));
                        break;
                    case LOG_OUT_REQUEST:
                        LogoutResponseDto response = authenticationServiceWrapper.logOutUser((LogoutRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.LOG_OUT_RESPONSE, response));
                        break;
                    case ADD_SERVER_REQUEST:
                        ServerAdditionResponseDto serverAdditionResponseDto = serverManagementServiceWrapper.addServer((ServerAdditionRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.ADD_SERVER_RESPONSE, serverAdditionResponseDto));
                        break;
                    case ADD_CHANNEL_REQUEST:
                        ChannelAdditionResponseDto channelAdditionResponseDto = serverManagementServiceWrapper.addChannel((ChannelAdditionRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.ADD_CHANNEL_RESPONSE, channelAdditionResponseDto));
                        break;
                    case ADD_CATEGORY_REQUEST:
                        CatagoryAdditionResponseDto catagoryAdditionResponseDto = serverManagementServiceWrapper.addCategory((CategoryAdditionRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.ADD_CATEGORY_RESPONSE, catagoryAdditionResponseDto));
                        break;
                    case REMOVE_SERVER_MEMBER_REQUEST:
                        RemoveServerMemberResponseDto removeServerMemberResponseDto = serverManagementServiceWrapper.removeServerMember((RemoveServerMemberRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.REMOVE_SERVER_MEMBER_RESPONSE, removeServerMemberResponseDto));
                        break;
                    case ADD_PERSON_REQUEST:
                        PersonAdditionResponseDto personAdditionResponseDto = serverManagementServiceWrapper.addPerson((PersonAdditionRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.ADD_PERSON_RESPONSE, personAdditionResponseDto));
                        break;
                    case SET_ROLE_REQUEST:
                        SetRoleResponseDto setRoleResponseDto = serverManagementServiceWrapper.setRole((SetRoleRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.SET_ROLE_RESPONSE, setRoleResponseDto));
                        break;
                    case UPDATE_PERSON_REQUEST:
                        UpdatePersonResponseDto updatePersonResponseDto = userManagementServiceWrapper.updatePerson((UpdatePersonRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.UPDATE_PERSON_RESPONSE, updatePersonResponseDto));
                        break;
                    case CHANGE_PWD_REQUEST:
                        ChangePasswordResponseDto changePasswordResponseDto = userManagementServiceWrapper.changePassword((ChangePasswordRequestdto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.CHANGE_PWD_RESPONSE, changePasswordResponseDto));
                        break;
                    case CHANGE_IMG_REQUEST:
                        ChangePhotoResponseDto changePhotoResponseDto = userManagementServiceWrapper.changePhoto((ChangePhotoRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.CHANGE_IMG_RESPONSE, changePhotoResponseDto));
                        break;
                    case GET_FRIEND_LIST_REQUEST:
                        GetFriendListResponseDto friendList = userManagementServiceWrapper.getFriendList((GetFriendListRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_FRIEND_LIST_RESPONSE, friendList));
                        break;
                    case GET_BLOCK_LIST_REQUEST:
                        GetBlockListResponseDto blockedList = userManagementServiceWrapper.getBlockedList((GetBlockListRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_BLOCK_LIST_RESPONSE, blockedList));
                        break;
                    case GET_FRIEND_REQUESTS_REQUEST:
                        GetFriendRequestsResponseDto friendRequests = userManagementServiceWrapper.getFriendRequests((GetFriendRequestsRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_FRIEND_REQUESTS_RESPONSE, friendRequests));
                        break;
                    case GET_CHATS_REQUEST:
                        GetPrivateChatsResponseDto chats = userManagementServiceWrapper.getChats((GetPrivateChatsRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_CHATS_RESPONSE, chats));
                        break;
                    case GET_SERVERS_LIST_REQUEST:
                        GetServersListResponseDto serversList = userManagementServiceWrapper.getServersList((GetServersListRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.GET_SERVERS_LIST_RESPONSE, serversList));
                        break;
                    case SEND_FRIEND_REQUEST_REQUEST:
                        SendFriendRequestResponseDto sendFriendRequestResponseDto = chatManagementServiceWrapper.sendFriendRequest((SendFriendRequestRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.SEND_FRIEND_REQUEST_RESPONSE, sendFriendRequestResponseDto));
                        break;
                    case ACCEPT_FRIEND_REQUEST_REQUEST:
                        AcceptFriendRequestResponseDto acceptFriendRequestResponseDto = chatManagementServiceWrapper.acceptFriendRequest((AcceptFriendRequestRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.ACCEPT_FRIEND_REQUEST_RESPONSE, acceptFriendRequestResponseDto));
                        break;
                    case REJECT_FRIEND_REQUEST_REQUEST:
                        RejectFriendRequestResponseDto rejectFriendRequestResponseDto = chatManagementServiceWrapper.rejectFriendRequest((RejectFriendRequestRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.REJECT_FRIEND_REQUEST_RESPONSE, rejectFriendRequestResponseDto));
                        break;
                    case BLOCK_REQUEST:
                        BlockResponseDto blockResponseDto = chatManagementServiceWrapper.block((BlockRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.BLOCK_RESPONSE, blockResponseDto));
                        break;
                    case UNBLOCK_REQUEST:
                        UnblockResponseDto unblockResponseDto = chatManagementServiceWrapper.unblock((UnblockRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.UNBLOCK_RESPONSE, unblockResponseDto));
                        break;
                    case SEND_PRIVATE_MESSAGE_REQUEST:
                        SendPrivateMessageResponseDto sendPrivateMessageResponseDto = chatManagementServiceWrapper.sendPrivateMessage((SendPrivateMessageRequestDto) generalMessage.getPayload());
                        oos.writeObject(new GeneralDto<>(HeaderType.SEND_PRIVATE_MESSAGE_RESPONSE, sendPrivateMessageResponseDto));
                        break;

                }
                oos.flush();
                if (!handlerStatus.getStatus().equals(ClientHandlerStatusType.DISABLE))
                    handlerStatus.setStatus(ClientHandlerStatusType.RUNNING);
                maxMessagingError = 5;
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.warn("Couldn't send or receive message from client correctly");
                maxMessagingError--;
                if (maxMessagingError < 0)
                    currentThread.stop();
            } catch (RuntimeException runtimeException) {
                try {
                    oos.writeObject(new GeneralDto<>(HeaderType.ERROR, null));
                } catch (IOException e) {
                    maxMessagingError--;
                    if (maxMessagingError < 0)
                        currentThread.stop();
                }
            }
        }
    }

    private void shutdown() throws IOException {
        LOGGER.info("Shutdown client socket {}", socket.toString());
        oos.close();
        ois.close();
        socket.close();
        observable.firePropertyChange("ClientHandler", null, this);
        LOGGER.info("Client socket {} shutdown gracefully", socket.toString());
    }

    @Override
    @SuppressWarnings("BusyWait")
    public synchronized void propertyChange(PropertyChangeEvent evt) {
        ApplicationServerInfo serverInfo = (ApplicationServerInfo) evt.getNewValue();
        if (serverInfo.getServerStatus().equals(ServerStatus.SHUTDOWN)) {
            if (currentThread != null)
                //noinspection deprecation
                currentThread.stop();
            try {
                while (handlerStatus.getStatus().equals(ClientHandlerStatusType.BUSY))
                    Thread.sleep(100);
                oos.flush();
                GeneralDto<String> message = new GeneralDto<>(HeaderType.SERVER_SHUTDOWN, null);
                oos.writeObject(message);
                oos.flush();
                handlerStatus.setStatus(ClientHandlerStatusType.DISABLE);
                shutdown();
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Couldn't close socket {} gracefully", socket.toString());
            }
        }
    }

    public ClientHandlerStatus getHandlerStatus() {
        return handlerStatus;
    }

    public void setHandlerStatus(ClientHandlerStatusType handlerStatus) {
        this.handlerStatus.setStatus(handlerStatus);
    }
}
