package org.ce.ap.discord.common.entity.business.discord;

import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Category implements Serializable {

    private static final long serialVersionUID = -4227715898964317365L;

    private Map<String, DiscordChannel> channels;
    private String name;
    private final UUID id;

    public Category(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        channels = new HashMap<>();
    }

    public Map<String, DiscordChannel> getChannels() {
        return channels;
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

    public void addChannel(DiscordChannel channel) {
        channels.put(channel.getId().toString(), channel);
    }

    public void removeChannel(String id) {
        channels.remove(id);
    }
}
