package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ChannelAdditionResponseDto implements Serializable {

    private static final long serialVersionUID = -8844120773278887857L;

    private final boolean successful;
    private final String failureCause;

    public ChannelAdditionResponseDto(boolean successful, String failerCause) {
        this.successful = successful;
        this.failureCause = failerCause;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFailureCause() {
        return failureCause;
    }
}
