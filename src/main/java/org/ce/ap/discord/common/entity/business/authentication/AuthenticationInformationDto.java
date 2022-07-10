package org.ce.ap.discord.common.entity.business.authentication;

/**
 * @author Parham Ahmady
 * @since 5/30/2022
 */
public class AuthenticationInformationDto {
    private final String name;
    private final String id; //username
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final byte[] photo;

    public AuthenticationInformationDto(String id, String password) {
        this(null, id, password, null, null, null);
    }

    public AuthenticationInformationDto(String name, String id, String password, String email, String phoneNumber, byte[] photo) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }
}
