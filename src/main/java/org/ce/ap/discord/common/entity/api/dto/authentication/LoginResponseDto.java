package org.ce.ap.discord.common.entity.api.dto.authentication;

import org.ce.ap.discord.common.entity.business.Person;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class LoginResponseDto implements Serializable {

    private static final long serialVersionUID = -565341156981746461L;

    private final Person person;
    private final boolean successful;
    private final String message;

    public LoginResponseDto(Person person, boolean successful, String message) {
        this.person = person;
        this.successful = successful;
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
