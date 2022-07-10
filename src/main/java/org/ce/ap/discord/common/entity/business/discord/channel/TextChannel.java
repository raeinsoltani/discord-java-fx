package org.ce.ap.discord.common.entity.business.discord.channel;

import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Negar Anabestani
 */
public class TextChannel extends DiscordChannel {
    private final Map<String, DiscordMessage> discordMessages;
    private final Map<String, List<Reaction>> messageReactionMap;
    private final List<DiscordMessage> pinedDiscordMessages;


    public TextChannel(String name) {
        super(name);
        discordMessages = new HashMap<>();
        messageReactionMap = new HashMap<>();
        pinedDiscordMessages = new ArrayList<>();
    }

    public Map<String, DiscordMessage> getDiscordMessages() {
        return discordMessages;
    }

    public List<DiscordMessage> getPinedMessages() {
        return pinedDiscordMessages;
    }

    public Map<String, List<Reaction>> getMessageReactionMap() {
        return messageReactionMap;
    }

    public void sendMessage(DiscordMessage message) {
        discordMessages.put(message.getId().toString(), message);
    }

}
