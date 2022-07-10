package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetBlockListRequestDto implements Serializable {

    private static final long serialVersionUID = -7881184103433422503L;

    private final String personID;

    public GetBlockListRequestDto(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
