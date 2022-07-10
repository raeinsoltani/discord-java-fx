package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

public class GetServerChannelsRequestDto implements Serializable {

    private static final long serialVersionUID = -7255238647682391488L;

    private final String serverId;

    public GetServerChannelsRequestDto(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }
}
