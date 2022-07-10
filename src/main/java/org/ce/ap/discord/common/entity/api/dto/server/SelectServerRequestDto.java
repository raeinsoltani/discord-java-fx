package org.ce.ap.discord.common.entity.api.dto.server;

public class SelectServerRequestDto {
    private String serverId;
    public SelectServerRequestDto(String serverId){
        this.serverId=serverId;
    }

    public String getServerId() {
        return serverId;
    }
}
