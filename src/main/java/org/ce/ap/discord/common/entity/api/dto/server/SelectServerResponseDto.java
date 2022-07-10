package org.ce.ap.discord.common.entity.api.dto.server;

import org.ce.ap.discord.common.entity.business.discord.DiscordServer;

public class SelectServerResponseDto {
    private final boolean successful;
    private final String failureCause;
    private final DiscordServer server;

    public SelectServerResponseDto(boolean successful, String failureCause, DiscordServer server) {
        this.successful = successful;
        this.failureCause = failureCause;
        this.server = server;
    }

    public DiscordServer getServer() {
        return server;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
