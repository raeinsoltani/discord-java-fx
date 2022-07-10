package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/28/2022
 */
public class UpdatePersonRequestDto implements Serializable {

    private static final long serialVersionUID = -4130026650679642261L;

    private final String id;

    public UpdatePersonRequestDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
