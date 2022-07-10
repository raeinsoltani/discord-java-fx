package org.ce.ap.discord.common.entity.api.dto.server;

public class SendChannelMessageResponseDto {
    private final boolean successful;
    private final String failureCause;

    public SendChannelMessageResponseDto(boolean successful, String failureCause) {
        this.successful = successful;
        this.failureCause = failureCause;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
