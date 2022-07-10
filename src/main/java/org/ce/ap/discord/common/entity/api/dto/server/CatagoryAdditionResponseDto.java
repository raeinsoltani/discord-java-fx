package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class CatagoryAdditionResponseDto implements Serializable {

    private static final long serialVersionUID = -7035079179417715058L;

    private final boolean successful;
    private final String failerCause;

    public CatagoryAdditionResponseDto(boolean successful, String failerCause) {
        this.successful = successful;
        this.failerCause = failerCause;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFailerCause() {
        return failerCause;
    }
}
