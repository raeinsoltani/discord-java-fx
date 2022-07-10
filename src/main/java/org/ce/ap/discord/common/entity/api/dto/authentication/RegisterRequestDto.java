package org.ce.ap.discord.common.entity.api.dto.authentication;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class RegisterRequestDto implements Serializable {

    private static final long serialVersionUID = 4836621592670686074L;

    private final String name;
    private final String id; //username
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final byte[] photo;

    public RegisterRequestDto(String name, String id, String password, String email, String phoneNumber, byte[] photo) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
