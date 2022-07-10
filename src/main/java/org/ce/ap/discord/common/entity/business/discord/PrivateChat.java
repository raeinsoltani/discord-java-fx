package org.ce.ap.discord.common.entity.business.discord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ce.ap.discord.common.entity.business.Person;

/**
 * @author Farham Aman
 * @since 5/30/2022
 */
public class PrivateChat implements Serializable {
    private final String id;
    private Person person1;
    private Person person2;
    private List<DiscordMessage> discordMessages;

    public PrivateChat(Person person1, Person person2) {
        this.person1 = person1;
        this.person2 = person2;
        if(person1.getId().compareTo(person2.getId()) < 0) {
            this.id = person1.getId() + "-" +  person2.getId();
        } else {
            this.id = person2.getId() + "-" + person1.getId();
        }
        discordMessages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<DiscordMessage> getMessages() {
        return discordMessages;
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }
}
