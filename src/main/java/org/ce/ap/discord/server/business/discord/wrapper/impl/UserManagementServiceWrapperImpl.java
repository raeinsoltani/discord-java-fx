package org.ce.ap.discord.server.business.discord.wrapper.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.dto.user.*;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;
import org.ce.ap.discord.server.business.discord.UserManagementService;
import org.ce.ap.discord.server.business.discord.impl.UserManagementServiceImpl;
import org.ce.ap.discord.server.business.discord.wrapper.UserManagementServiceWrapper;

import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class UserManagementServiceWrapperImpl implements UserManagementServiceWrapper, InitializingBean {
    private UserManagementService userManagementService;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        userManagementService = (UserManagementService) context.getApplicationBeans().get(UserManagementServiceImpl.class);
    }

    @Override
    public UpdatePersonResponseDto updatePerson(UpdatePersonRequestDto request) {
        Person person = userManagementService.updatePerson(request.getId());
        return new UpdatePersonResponseDto(person);
    }

    @Override
    public ChangePasswordResponseDto changePassword(ChangePasswordRequestdto request) {
        userManagementService.changePassword(request.getPersonID(), request.getNewPassword());
        return new ChangePasswordResponseDto();
    }

    @Override
    public ChangePhotoResponseDto changePhoto(ChangePhotoRequestDto request) {
        userManagementService.changePhoto(request.getPersonID(), request.getNewPhoto());
        return new ChangePhotoResponseDto();
    }

    @Override
    public GetFriendListResponseDto getFriendList(GetFriendListRequestDto request) {
        List<Person> friendList = userManagementService.getFriendList(request.getPersonID());
        return new GetFriendListResponseDto(friendList);
    }

    @Override
    public GetBlockListResponseDto getBlockedList(GetBlockListRequestDto request) {
        List<String> blockedList = userManagementService.getBlockedList(request.getPersonID());
        return new GetBlockListResponseDto(blockedList);
    }

    @Override
    public GetFriendRequestsResponseDto getFriendRequests(GetFriendRequestsRequestDto request) {
        List<String> friendRequests = userManagementService.getFriendRequests(request.getPersonID());
        return new GetFriendRequestsResponseDto(friendRequests);
    }

    @Override
    public GetPrivateChatsResponseDto getChats(GetPrivateChatsRequestDto request) {
        List<PrivateChat> chats = userManagementService.getChats(request.getPersonID());
        return new GetPrivateChatsResponseDto(chats);
    }

    @Override
    public GetServersListResponseDto getServersList(GetServersListRequestDto request) {
        List<String> serversList = userManagementService.getServersList(request.getPersonID());
        return new GetServersListResponseDto(serversList);
    }
}
