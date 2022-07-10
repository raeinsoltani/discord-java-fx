package org.ce.ap.discord.common.entity.api.dto.authentication;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class LogoutResponseDto implements Serializable {

    private static final long serialVersionUID = 3932054099305870920L;

    private final boolean successful;

    public LogoutResponseDto(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
