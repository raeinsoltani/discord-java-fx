package org.ce.ap.discord.common.entity.api.dto.server;

public class RenameServerRequestDto {
    private String serverId;
    private String name;
    public RenameServerRequestDto(String serverId,String name){
        this.serverId=serverId;
        this.name=name;
    }

    public String getServerId() {
        return serverId;
    }

    public String getName() {
        return name;
    }
}
