package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class PersonAdditionResponseDto implements Serializable {

    private static final long serialVersionUID = 3273644415913069090L;

    private final boolean successful;
    private final String failureCause;

    public PersonAdditionResponseDto(boolean successful, String failureCause) {
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
