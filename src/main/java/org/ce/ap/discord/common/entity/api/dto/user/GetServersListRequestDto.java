package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetServersListRequestDto implements Serializable {

    private static final long serialVersionUID = -5620743357290233525L;

    private final String personID;

    public GetServersListRequestDto(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
