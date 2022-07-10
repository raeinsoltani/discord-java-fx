package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;
import java.util.List;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class GetBlockListResponseDto implements Serializable {

    private static final long serialVersionUID = 8656998934540769376L;

    private final List<String> ids;

    public GetBlockListResponseDto(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }
}
