package org.ce.ap.discord.common.entity.api.dto.authentication;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class LoginRequestDto implements Serializable {

    private static final long serialVersionUID = -6031522030079230742L;

    private final String id; //username
    private final String password;

    public LoginRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
