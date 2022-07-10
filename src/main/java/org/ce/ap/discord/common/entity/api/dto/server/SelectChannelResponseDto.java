package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;

public class SelectChannelResponseDto {
    private final boolean successful;
    private final String failureCause;
    private DiscordChannel channel;
    public SelectChannelResponseDto(boolean successful,String failureCause,DiscordChannel channel){
        this.channel=channel;
        this.failureCause=failureCause;
        this.successful=successful;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public DiscordChannel getChannel() {
        return channel;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
