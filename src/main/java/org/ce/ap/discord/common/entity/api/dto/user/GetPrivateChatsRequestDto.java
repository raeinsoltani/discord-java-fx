package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetPrivateChatsRequestDto implements Serializable {

    private static final long serialVersionUID = 2448389605997985877L;

    private final String personID;

    public GetPrivateChatsRequestDto(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
