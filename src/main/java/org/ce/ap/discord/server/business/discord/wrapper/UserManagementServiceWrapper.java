package org.ce.ap.discord.server.business.discord.wrapper;

import org.ce.ap.discord.common.entity.api.dto.user.*;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public interface UserManagementServiceWrapper {

    UpdatePersonResponseDto updatePerson(UpdatePersonRequestDto request);

    ChangePasswordResponseDto changePassword(ChangePasswordRequestdto request);

    ChangePhotoResponseDto changePhoto(ChangePhotoRequestDto request);

    GetFriendListResponseDto getFriendList(GetFriendListRequestDto request);

    GetBlockListResponseDto getBlockedList(GetBlockListRequestDto request);

    GetFriendRequestsResponseDto getFriendRequests(GetFriendRequestsRequestDto request);

    GetPrivateChatsResponseDto getChats(GetPrivateChatsRequestDto request);

    GetServersListResponseDto getServersList(GetServersListRequestDto request);
}
