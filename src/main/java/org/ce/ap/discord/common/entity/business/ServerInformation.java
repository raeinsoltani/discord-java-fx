package org.ce.ap.discord.common.entity.business;

import org.ce.ap.discord.common.entity.business.discord.DiscordServer;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Parham Ahmady
 * @since 5/30/2022
 */
public class ServerInformation implements Serializable {

    private static final long serialVersionUID = 6569040704842959735L;

    private final HashMap<String, Person> persons;
    private final Map<String, DiscordServer> servers;
    private final Map<String, PrivateChat> chats;

    public ServerInformation() {
        this.persons = new HashMap<>();
        this.servers = new HashMap<>();
        this.chats = new HashMap<>();
    }

    public Map<String, DiscordServer> getServers() {
        return servers;
    }

    public HashMap<String, Person> getPersons() {
        return persons;
    }

    public Map<String, PrivateChat> getChats() {
        return chats;
    }
}
