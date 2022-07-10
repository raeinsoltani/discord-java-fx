package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetFriendRequestsRequestDto implements Serializable {

    private static final long serialVersionUID = 2640641821267772694L;

    private final String personID;

    public GetFriendRequestsRequestDto(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
