package org.ce.ap.discord.common.entity.business.discord;

import org.ce.ap.discord.common.entity.business.Person;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Farhad Aman
 * @since 6/27/2022
 */
public class DiscordMessage<T> implements Serializable {

    private static final long serialVersionUID = -5685775088751293240L;

    private final Person sender;
    private final UUID id;
    private final T body;

    public DiscordMessage(Person sender, T body) {
        this.sender = sender;
        this.body=body;
        id=UUID.randomUUID();
    }

    public Person getSender() {
        return sender;
    }

    public T getBody() {
        return body;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return sender.getId() + " : " + body;
    }
}
