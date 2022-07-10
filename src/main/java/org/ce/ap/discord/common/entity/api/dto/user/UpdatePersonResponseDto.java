package org.ce.ap.discord.common.entity.api.dto.user;

import org.ce.ap.discord.common.entity.business.Person;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/28/2022
 */
public class UpdatePersonResponseDto implements Serializable {

    private static final long serialVersionUID = 3127746455328621059L;

    private final Person person;

    public UpdatePersonResponseDto(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
