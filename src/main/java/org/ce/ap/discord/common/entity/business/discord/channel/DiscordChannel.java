package org.ce.ap.discord.common.entity.business.discord.channel;

import org.ce.ap.discord.common.entity.business.discord.DiscordServer;

import java.io.Serializable;
import java.util.UUID;

public abstract class DiscordChannel implements Serializable {

    private static final long serialVersionUID = 1550107106145930260L;

    private final UUID id;
    private String name;

    public DiscordChannel(String name){
        this.id=UUID.randomUUID();
        this.name=name;
    }
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
