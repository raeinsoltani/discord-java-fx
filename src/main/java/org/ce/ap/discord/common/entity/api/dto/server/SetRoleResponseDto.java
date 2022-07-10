package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class SetRoleResponseDto implements Serializable {

    private static final long serialVersionUID = -3988397401243549439L;

    private final boolean successful;
    private final String failureCause;

    public SetRoleResponseDto(boolean successful, String failureCause) {
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
