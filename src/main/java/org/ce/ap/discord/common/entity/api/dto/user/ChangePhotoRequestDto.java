package org.ce.ap.discord.common.entity.api.dto.user;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ChangePhotoRequestDto implements Serializable {

    private static final long serialVersionUID = -4161432423035690473L;

    private final String personID;
    private final byte[] newPhoto;

    public ChangePhotoRequestDto(String personID, byte[] newPhoto) {
        this.personID = personID;
        this.newPhoto = newPhoto;
    }

    public String getPersonID() {
        return personID;
    }

    public byte[] getNewPhoto() {
        return newPhoto;
    }
}
