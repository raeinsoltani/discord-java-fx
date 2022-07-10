package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ServerAdditionResponseDto implements Serializable {

    private static final long serialVersionUID = -8392420654291066448L;

    private final boolean successful;

    public ServerAdditionResponseDto(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
