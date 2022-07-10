package org.ce.ap.discord.common.entity.api.dto.chat;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class AcceptFriendRequestResponseDto implements Serializable {

    private static final long serialVersionUID = -1990834059613810224L;

    private final boolean successful;
    private final String failureCause;

    public AcceptFriendRequestResponseDto(boolean successful, String failureCause) {
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
