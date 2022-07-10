package org.ce.ap.discord.common.entity.api.dto.server;

public class SelectChannelRequestDto {
    String serverId;
    String categoryId;
    String ChannelId;
    public SelectChannelRequestDto(String serverId,String categoryId,String channelId){
        this.serverId=serverId;
        this.ChannelId=channelId;
        this.categoryId=categoryId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getChannelId() {
        return ChannelId;
    }
}
