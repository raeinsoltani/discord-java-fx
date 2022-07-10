package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class RemoveChannelResponseDto implements Serializable {

    private static final long serialVersionUID = -1754932590913427497L;

    private final boolean successful;
    private final String failureCause;

    public RemoveChannelResponseDto(boolean successful, String failureCause) {
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
