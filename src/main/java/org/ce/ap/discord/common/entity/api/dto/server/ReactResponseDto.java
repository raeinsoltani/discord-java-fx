package org.ce.ap.discord.common.entity.api.dto.server;

public class ReactResponseDto {
    private final boolean successful;
    private final String failureCause;

    public ReactResponseDto(boolean successful, String failureCause) {
        this.successful = successful;
        this.failureCause = failureCause;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFailureCause() {
        return failureCause;
    }
}
