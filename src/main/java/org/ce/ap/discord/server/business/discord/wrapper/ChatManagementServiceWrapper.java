package org.ce.ap.discord.server.business.discord.wrapper;

import org.ce.ap.discord.common.entity.api.dto.chat.*;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public interface ChatManagementServiceWrapper {

    SendFriendRequestResponseDto sendFriendRequest(SendFriendRequestRequestDto request);

    AcceptFriendRequestResponseDto acceptFriendRequest(AcceptFriendRequestRequestDto request);

    RejectFriendRequestResponseDto rejectFriendRequest(RejectFriendRequestRequestDto request);

    BlockResponseDto block(BlockRequestDto request);

    UnblockResponseDto unblock(UnblockRequestDto request);

    SendPrivateMessageResponseDto sendPrivateMessage(SendPrivateMessageRequestDto request);

}
