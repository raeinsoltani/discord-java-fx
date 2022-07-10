package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetFriendRequestsResponseDto implements Serializable {

    private static final long serialVersionUID = -5229955334770560448L;

    private final List<String> ids;

    public GetFriendRequestsResponseDto(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }
}
