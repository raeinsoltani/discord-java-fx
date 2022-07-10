package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetFriendListRequestDto implements Serializable {

    private static final long serialVersionUID = 4299851267870919622L;

    private final String personID;

    public GetFriendListRequestDto(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
