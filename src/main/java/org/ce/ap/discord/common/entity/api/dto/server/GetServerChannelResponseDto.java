package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.discord.Category;

import java.util.List;

public class GetServerChannelResponseDto {
    private List<Category> serverChannelsList;
    private final boolean successful;
    private final String failureCause;

    public GetServerChannelResponseDto(boolean successful, String failureCause,List<Category> serverChannelsList) {
        this.successful = successful;
        this.failureCause = failureCause;
        this.serverChannelsList=serverChannelsList;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public List<Category> getServerChannelsList() {
        return serverChannelsList;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
