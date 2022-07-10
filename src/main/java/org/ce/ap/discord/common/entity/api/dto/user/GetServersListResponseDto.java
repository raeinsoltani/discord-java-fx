package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetServersListResponseDto implements Serializable {

    private static final long serialVersionUID = 6511430312617165153L;

    private final List<String> serverIds;

    public GetServersListResponseDto(List<String> serverIds) {
        this.serverIds = serverIds;
    }

    public List<String> getServerIds() {
        return serverIds;
    }
}
