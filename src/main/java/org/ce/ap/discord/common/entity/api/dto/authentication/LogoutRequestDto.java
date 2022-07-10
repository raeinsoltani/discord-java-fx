package org.ce.ap.discord.common.entity.api.dto.authentication;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class LogoutRequestDto implements Serializable {
    private static final long serialVersionUID = -8408087983099707677L;


    private final String personId;

    public LogoutRequestDto(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }
}
