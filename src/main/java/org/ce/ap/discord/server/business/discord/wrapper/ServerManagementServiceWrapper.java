package org.ce.ap.discord.server.business.discord.wrapper;

import org.ce.ap.discord.common.entity.api.dto.server.*;

/**
 * @author Parham Ahmady,Negar Anabestani
 * @since 6/27/2022
 */
public interface ServerManagementServiceWrapper {

    ServerAdditionResponseDto addServer(ServerAdditionRequestDto request);

    ChannelAdditionResponseDto addChannel(ChannelAdditionRequestDto request);

    CatagoryAdditionResponseDto addCategory(CategoryAdditionRequestDto request);

    RemoveServerMemberResponseDto removeServerMember(RemoveServerMemberRequestDto request);

    RemoveChannelResponseDto removeChannel(RemoveChannelRequestDto request);

    PersonAdditionResponseDto addPerson(PersonAdditionRequestDto request);

    SendChannelMessageResponseDto sendChannelMessage(SendChannelMessageRequestDto request);

    SelectServerResponseDto selectServer(SelectServerRequestDto request);

    SetRoleResponseDto setRole(SetRoleRequestDto request);

    GetServerMembersResponseDto getServerMembers(GetServerMembersRequestDto request);

    RenameServerResponseDto renameServer(RenameServerRequestDto request);

    SelectChannelResponseDto selectChannel(SelectChannelRequestDto request);

    GetServerChannelResponseDto getServerChannels(GetServerChannelsRequestDto request);

    ReactResponseDto react(ReactRequestDto request);
}