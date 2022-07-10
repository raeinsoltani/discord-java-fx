package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ChangePasswordRequestdto implements Serializable {

    private static final long serialVersionUID = -1227093500478359829L;

    private final String personID;
    private final String newPassword;

    public ChangePasswordRequestdto(String personID, String newPassword) {
        this.personID = personID;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPersonID() {
        return personID;
    }
}
