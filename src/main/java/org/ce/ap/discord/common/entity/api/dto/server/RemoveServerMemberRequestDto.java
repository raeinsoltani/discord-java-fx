package org.ce.ap.discord.common.entity.api.dto.server;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class RemoveServerMemberRequestDto implements Serializable {

    private static final long serialVersionUID = -1134098034001928300L;

    private final String doerId;
    private final String personId;
    private final String serverId;

    public RemoveServerMemberRequestDto(String doerId, String personId, String serverId) {
        this.doerId = doerId;
        this.personId = personId;
        this.serverId = serverId;
    }

    public String getDoerId() {
        return doerId;
    }

    public String getPersonId() {
        return personId;
    }

    public String getServerId() {
        return serverId;
    }
}
