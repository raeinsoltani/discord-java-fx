package org.ce.ap.discord.common.entity.api.dto.authentication;

import org.ce.ap.discord.common.entity.business.Person;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class RegisterResponseDto implements Serializable {

    private static final long serialVersionUID = -1448991817557471339L;

    private final Person person;
    private final boolean successful;
    private final String failCauseMessage;

    public RegisterResponseDto(Person person, boolean successful, String failCauseMessage) {
        this.person = person;
        this.successful = successful;
        this.failCauseMessage = failCauseMessage;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFailCauseMessage() {
        return failCauseMessage;
    }
}
