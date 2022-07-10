package org.ce.ap.discord.server.business.discord;

import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.Category;
import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;
import org.ce.ap.discord.common.entity.business.discord.DiscordServer;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.enumeration.Ability;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;
import org.ce.ap.discord.server.business.authentication.exceptions.UsernameNotFoundException;
import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;
import org.ce.ap.discord.server.business.discord.exceptions.MissingAbilityException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Negar Anabestani
 * @since 5/30/2022
 */
public interface ServerManagementService {

    DiscordServer selectServer(String serverId) throws InvalidIdException;

    boolean sendMessage(String serverId, String categoryId, String channelId, DiscordMessage message) throws InvalidIdException;

    boolean addServer(String personId, String name);

    void react(DiscordChannel channel, String messageId, Reaction react) throws InvalidIdException;

    List<Category> getServerChannels(String serverId) throws InvalidIdException;

    List<Person> getServerMembers(String serverId) throws InvalidIdException;

    boolean addChannel(String personId, String serverId, String categoryId, String name, ChannelType type) throws MissingAbilityException;

    boolean addCategory(String serverId, String name, String personId) throws MissingAbilityException;

    boolean removeServerMember(String serverId, String doerId, String personId) throws MissingAbilityException;

    boolean removeChannel(String serverId, String categoryId, String channelId, String personId) throws MissingAbilityException;

    void renameServer(String serverId, String name) throws InvalidIdException;

    DiscordChannel selectChannel(String serverId, String categoryId, String channelId) throws InvalidIdException;

    boolean addPerson(String serverId, String doerId, String personId) throws MissingAbilityException, UsernameNotFoundException;

    boolean setRole(String doerId, String serverId, String personId, ArrayList<Ability> abilities) throws MissingAbilityException;

    boolean limitChannels(String personId, String serverId, String doerId, List<String> limitedChannelIds) throws MissingAbilityException;
}
