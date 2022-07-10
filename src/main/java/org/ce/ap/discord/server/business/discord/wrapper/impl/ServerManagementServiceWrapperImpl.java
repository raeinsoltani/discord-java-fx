package org.ce.ap.discord.server.business.discord.wrapper.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.dto.server.*;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.server.business.authentication.exceptions.UsernameNotFoundException;
import org.ce.ap.discord.server.business.discord.ServerManagementService;
import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;
import org.ce.ap.discord.server.business.discord.exceptions.MissingAbilityException;
import org.ce.ap.discord.server.business.discord.impl.ServerManagementServiceImpl;
import org.ce.ap.discord.server.business.discord.wrapper.ServerManagementServiceWrapper;

/**
 * @author Parham Ahmady,Negar Anabestani
 * @since 6/27/2022
 */
public class ServerManagementServiceWrapperImpl implements ServerManagementServiceWrapper, InitializingBean {
    private ServerManagementService serverManagementService;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        serverManagementService = (ServerManagementService) context.getApplicationBeans().get(ServerManagementServiceImpl.class);
    }


    @Override
    public ServerAdditionResponseDto addServer(ServerAdditionRequestDto request) {
        final boolean result = serverManagementService.addServer(request.getPersonId(), request.getName());
        return new ServerAdditionResponseDto(result);
    }

    @Override
    public RenameServerResponseDto renameServer(RenameServerRequestDto request) {
        try {
            serverManagementService.renameServer(request.getServerId(), request.getName());
            return new RenameServerResponseDto(true, null);
        } catch (InvalidIdException e) {
            return new RenameServerResponseDto(false, e.getMessage());
        }
    }

    @Override
    public SelectChannelResponseDto selectChannel(SelectChannelRequestDto request) {
        try {
            return new SelectChannelResponseDto(true, null, serverManagementService.selectChannel(request.getServerId(), request.getCategoryId(), request.getChannelId()));
        } catch (InvalidIdException e) {
            return new SelectChannelResponseDto(false, e.getMessage(), null);
        }
    }

    @Override
    public GetServerChannelResponseDto getServerChannels(GetServerChannelsRequestDto request) {
        try {
            return new GetServerChannelResponseDto(true, null, serverManagementService.getServerChannels(request.getServerId()));
        } catch (InvalidIdException e) {
            return new GetServerChannelResponseDto(false, e.getMessage(), null);
        }
    }

    @Override
    public ChannelAdditionResponseDto addChannel(ChannelAdditionRequestDto request) {
        try {
            serverManagementService.addChannel(request.getPersonId(), request.getServerId(), request.getCategoryId(), request.getName(), request.getType());
            return new ChannelAdditionResponseDto(true, null);
        } catch (MissingAbilityException e) {
            return new ChannelAdditionResponseDto(false, e.getMessage());
        }
    }

    @Override
    public CatagoryAdditionResponseDto addCategory(CategoryAdditionRequestDto request) {
        try {
            serverManagementService.addCategory(request.getServerId(), request.getName(), request.getPersonId());
            return new CatagoryAdditionResponseDto(true, null);
        } catch (MissingAbilityException e) {
            return new CatagoryAdditionResponseDto(false, e.getMessage());
        }
    }

    @Override
    public RemoveServerMemberResponseDto removeServerMember(RemoveServerMemberRequestDto request) {
        try {
            serverManagementService.removeServerMember(request.getDoerId(), request.getPersonId(), request.getServerId());
            return new RemoveServerMemberResponseDto(true, null);
        } catch (MissingAbilityException e) {
            return new RemoveServerMemberResponseDto(false, e.getMessage());
        }
    }

    @Override
    public RemoveChannelResponseDto removeChannel(RemoveChannelRequestDto request) {
        try {
            serverManagementService.removeChannel(request.getServerId(), request.getCategoryId(), request.getChannelId(), request.getPersonId());
            return new RemoveChannelResponseDto(true, null);
        } catch (MissingAbilityException e) {
            return new RemoveChannelResponseDto(false, e.getMessage());
        }
    }

    @Override
    public PersonAdditionResponseDto addPerson(PersonAdditionRequestDto request) {
        try {
            serverManagementService.addPerson(request.getServerId(), request.getDoerId(), request.getPersonId());
            return new PersonAdditionResponseDto(true, null);
        } catch (MissingAbilityException | UsernameNotFoundException e) {
            return new PersonAdditionResponseDto(false, e.getMessage());
        }
    }

    @Override
    public GetServerMembersResponseDto getServerMembers(GetServerMembersRequestDto request) {
        try {
            return new GetServerMembersResponseDto(true, null, serverManagementService.getServerMembers(request.getServerId()));
        } catch (InvalidIdException e) {
            return new GetServerMembersResponseDto(false, e.getMessage(), null);
        }
    }

    @Override
    public SendChannelMessageResponseDto sendChannelMessage(SendChannelMessageRequestDto request) {
        try {
            serverManagementService.sendMessage(request.getServerId(), request.getCategoryId(), request.getChannelId(), request.getMessage());
            return new SendChannelMessageResponseDto(true, null);
        } catch (InvalidIdException e) {
            return new SendChannelMessageResponseDto(false, e.getMessage());
        }
    }

    @Override
    public SelectServerResponseDto selectServer(SelectServerRequestDto request) {
        try {
            return new SelectServerResponseDto(true, null, serverManagementService.selectServer(request.getServerId()));
        } catch (InvalidIdException e) {
            return new SelectServerResponseDto(false, e.getMessage(), null);
        }
    }

    @Override
    public SetRoleResponseDto setRole(SetRoleRequestDto request) {
        try {
            serverManagementService.setRole(request.getDoerId(), request.getServerId(), request.getPersonId(), request.getAbilities());
            return new SetRoleResponseDto(true, null);
        } catch (MissingAbilityException e) {
            return new SetRoleResponseDto(false, e.getMessage());
        }
    }

    @Override
    public ReactResponseDto react(ReactRequestDto request) {
        try {
            serverManagementService.react(request.getChannel(), request.getMessageId(), request.getReaction());
            return new ReactResponseDto(true, null);
        } catch (InvalidIdException e) {
            return new ReactResponseDto(false, e.getMessage());
        }
    }
}
