package org.ce.ap.discord.common.entity.api.dto.chat;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class RejectFriendRequestResponseDto implements Serializable {

    private static final long serialVersionUID = 1769550165059522267L;

    private final boolean successful;
    private final String failureCause;

    public RejectFriendRequestResponseDto(boolean successful, String failureCause) {
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
