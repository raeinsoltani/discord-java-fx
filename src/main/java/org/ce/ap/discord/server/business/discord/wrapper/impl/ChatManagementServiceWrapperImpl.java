package org.ce.ap.discord.server.business.discord.wrapper.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.dto.chat.*;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.server.business.discord.ChatManagementService;
import org.ce.ap.discord.server.business.discord.impl.ChatManagementServiceImpl;
import org.ce.ap.discord.server.business.discord.wrapper.ChatManagementServiceWrapper;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ChatManagementServiceWrapperImpl implements ChatManagementServiceWrapper, InitializingBean {

    private ChatManagementService chatManagementService;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        chatManagementService = (ChatManagementService) context.getApplicationBeans().get(ChatManagementServiceImpl.class);
    }

    @Override
    public SendFriendRequestResponseDto sendFriendRequest(SendFriendRequestRequestDto request) {
        try {
            chatManagementService.sendFriendRequest(request.getSenderId(), request.getReceiverId());
            return new SendFriendRequestResponseDto(true, null);
        } catch (Exception e) {
            return new SendFriendRequestResponseDto(false, e.getMessage());
        }
    }

    @Override
    public AcceptFriendRequestResponseDto acceptFriendRequest(AcceptFriendRequestRequestDto request) {
        try {
            chatManagementService.acceptFriendRequest(request.getSenderId(), request.getReceiverId());
            return new AcceptFriendRequestResponseDto(true, null);
        } catch (Exception e) {
            return new AcceptFriendRequestResponseDto(false, e.getMessage());
        }
    }

    @Override
    public RejectFriendRequestResponseDto rejectFriendRequest(RejectFriendRequestRequestDto request) {
        try {
            chatManagementService.rejectFriendRequest(request.getSenderId(), request.getReceiverId());
            return new RejectFriendRequestResponseDto(true, null);
        } catch (Exception e) {
            return new RejectFriendRequestResponseDto(false, e.getMessage());
        }
    }

    @Override
    public BlockResponseDto block(BlockRequestDto request) {
        try {
            chatManagementService.block(request.getSenderId(), request.getReceiverId());
            return new BlockResponseDto(true, null);
        } catch (Exception e) {
            return new BlockResponseDto(false, e.getMessage());
        }
    }

    @Override
    public UnblockResponseDto unblock(UnblockRequestDto request) {
        try {
            chatManagementService.unblock(request.getSenderId(), request.getReceiverId());
            return new UnblockResponseDto(true, null);
        } catch (Exception e) {
            return new UnblockResponseDto(false, e.getMessage());
        }
    }

    @Override
    public SendPrivateMessageResponseDto sendPrivateMessage(SendPrivateMessageRequestDto request) {
        try {
            chatManagementService.sendPrivateMessage(request.getSenderId(), request.getReceiverId(), request.getMessage());
            return new SendPrivateMessageResponseDto(true, null);
        } catch (Exception e) {
            return new SendPrivateMessageResponseDto(false, e.getMessage());
        }
    }
}
