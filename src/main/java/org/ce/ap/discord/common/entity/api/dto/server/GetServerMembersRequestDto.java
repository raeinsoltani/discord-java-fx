package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

public class GetServerMembersRequestDto implements Serializable {

    private static final long serialVersionUID = 4542732205712752620L;

    private final String serverId;

    public GetServerMembersRequestDto(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }
}
