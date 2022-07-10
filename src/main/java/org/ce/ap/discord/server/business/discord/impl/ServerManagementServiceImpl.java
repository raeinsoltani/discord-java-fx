package org.ce.ap.discord.server.business.discord.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.ServerInformation;
import org.ce.ap.discord.common.entity.business.discord.Category;
import org.ce.ap.discord.common.entity.business.discord.DiscordMessage;
import org.ce.ap.discord.common.entity.business.discord.DiscordServer;
import org.ce.ap.discord.common.entity.business.discord.Role;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.discord.channel.TextChannel;
import org.ce.ap.discord.common.entity.business.enumeration.Ability;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;
import org.ce.ap.discord.common.entity.business.enumeration.Reaction;
import org.ce.ap.discord.server.business.authentication.exceptions.UsernameNotFoundException;
import org.ce.ap.discord.server.business.discord.ServerManagementService;
import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;
import org.ce.ap.discord.server.business.discord.exceptions.MissingAbilityException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Negar Anabestani
 */
public class ServerManagementServiceImpl implements ServerManagementService, InitializingBean {
    private ServerInformation serverInformation;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        serverInformation = (ServerInformation) context.getApplicationBeans().get(ServerInformation.class);
    }

    @Override
    public void react(DiscordChannel channel, String messageId, Reaction react) throws InvalidIdException {
        if (channel instanceof TextChannel){
            ArrayList<Reaction> list=new ArrayList<>(((TextChannel) channel).getMessageReactionMap().get(messageId));
            list.add(react);
            ((TextChannel) channel).getMessageReactionMap().put(messageId,list);
        }else
            throw new InvalidIdException("channel type not valid");
    }
    @Override
    public List<Category> getServerChannels(String serverId) throws InvalidIdException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server == null)
            throw new InvalidIdException("serverId is not valid");
        else
            return new ArrayList<>(server.getCategories().values());
    }

    @Override
    public List<Person> getServerMembers(String serverId) throws InvalidIdException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server == null)
            throw new InvalidIdException("serverId is not valid");
        else
            return new ArrayList<>(server.getPersons().values());
    }

    @Override
    public DiscordServer selectServer(String serverId) throws InvalidIdException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server == null)
            throw new InvalidIdException("serverId is not valid");
        else
            return server;
    }

    @Override
    public boolean sendMessage(String serverId, String categoryId, String channelId, DiscordMessage message) throws InvalidIdException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server == null) {
            throw new InvalidIdException("serverId is invalid");
        } else {
            Category category = server.getCategories().get(categoryId);
            if (category == null) {
                throw new InvalidIdException("categoryId is invalid");
            } else {
                DiscordChannel channel = category.getChannels().get(channelId);
                if (channel == null)
                    throw new InvalidIdException("channelId is invalid");
                else if (channel instanceof TextChannel) {
                    ((TextChannel) channel).getDiscordMessages().put(message.getId().toString(),message);
                    return true;
                } else
                    throw new InvalidIdException("channelId is invalid");
            }
        }
    }

    @Override
    public boolean addServer(String personId, String name) {
        Person serverOwner = serverInformation.getPersons().get(personId);
        DiscordServer server = new DiscordServer(name, serverOwner);
        server.getPersons().put(personId, serverOwner);
        serverOwner.getServerIdList().add(server.getId().toString());
        serverInformation.getServers().put(String.valueOf(server.getId()), server);
        return true;
    }

    @Override
    public boolean addChannel(String personId, String serverId, String categoryId, String name, ChannelType type) throws MissingAbilityException {
        DiscordServer serverOfCat = serverInformation.getServers().get(serverId);
        Person doer = serverOfCat.getPersons().get(personId);
        if (doer.getServerRoleMap().get(serverId).getAbilities().contains(Ability.CREAT_CHANNEL)) {
            serverOfCat.addChannel(type, categoryId, name);
            return true;
        } else
            throw new MissingAbilityException("missing creat channel ability");
    }

    @Override
    public boolean removeChannel(String serverId, String categoryId, String channelId, String personId) throws MissingAbilityException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        Person person = serverInformation.getPersons().get(personId);
        Role role = server.getPersonRoleMap().get(person);
        if (role.getAbilities().contains(Ability.REMOVE_CHANNEL)) {
            Category category = server.getCategories().get(categoryId);
            category.removeChannel(channelId);
            return true;
        } else
            throw new MissingAbilityException("missing remove channel ability");
    }

    @Override
    public void renameServer(String serverId, String name) throws InvalidIdException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server == null)
            throw new InvalidIdException("serverId is invalid");
        else
            server.setName(name);
    }

    @Override
    public DiscordChannel selectChannel(String serverId, String categoryId, String channelId) throws InvalidIdException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server == null) {
            throw new InvalidIdException("serverId is invalid");
        } else {
            Category category = server.getCategories().get(categoryId);
            if (category == null) {
                throw new InvalidIdException("categoryId is invalid");
            } else {
                DiscordChannel channel = category.getChannels().get(channelId);
                if (channel == null)
                    throw new InvalidIdException("channelId is invalid");
                else
                    return channel;
            }
        }
    }

    @Override
    public boolean addPerson(String serverId, String doerId, String personId) throws MissingAbilityException, UsernameNotFoundException {
        Person person = serverInformation.getPersons().get(personId);
        if (person == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        DiscordServer server = serverInformation.getServers().get(serverId);
        Role newPersonRole = new Role();
        ArrayList<Ability> abilities = new ArrayList<>();
        abilities.add(Ability.PIN_CHANNEL_MESSAGE);
        abilities.add(Ability.CREAT_CHANNEL);
        abilities.add(Ability.RENAME_SERVER);
        abilities.add(Ability.REMOVE_SERVER_MEMBER);
        abilities.add(Ability.LIMIT_SERVER_MEMBER);
        abilities.add(Ability.LIMIT_CHANNEL_MEMBER);
        abilities.add(Ability.REMOVE_CHANNEL);
        abilities.add(Ability.VISIBLE_CHAT_HISTORY);
        newPersonRole.setAbilities(abilities);
        Person doer = server.getPersons().get(doerId);
        if (doer.getServerRoleMap().get(serverId).getAbilities().contains(Ability.REMOVE_SERVER_MEMBER)) {
            server.addPerson(person, newPersonRole);
            ArrayList<String> channels = new ArrayList<>();
            for (Category c : server.getCategories().values()) {
                for (DiscordChannel ch : c.getChannels().values()) {
                    channels.add(ch.getId().toString());
                }
            }
            person.getServerChannelMap().put(serverId, channels);
            DiscordChannel targetChannel = server.getCategories().get(server.getDefaultTextChannelCatId()).getChannels().get(server.getDefaultTextChannelId());
            if (targetChannel instanceof TextChannel) {
                String messageBody = "welcome " + person.getName();
                ((TextChannel) targetChannel).sendMessage(new DiscordMessage<String>(server.getAdmin(), messageBody));
            }
            return true;
        } else
            throw new MissingAbilityException("missing creat channel ability");

    }

    @Override
    public boolean addCategory(String serverId, String name, String personId) throws MissingAbilityException {
        DiscordServer serverOfCat = serverInformation.getServers().get(serverId);
        Person doer = serverOfCat.getPersons().get(personId);
        if (doer.getServerRoleMap().get(serverId).getAbilities().contains(Ability.CREAT_CHANNEL)) {
            serverOfCat.addCategory(name);
            return true;
        } else
            throw new MissingAbilityException("missing creat channel ability");
    }


    //todo send welcoming message

    @Override
    public boolean removeServerMember(String serverId, String doerId, String personId) throws MissingAbilityException {
        Person doer = serverInformation.getPersons().get(doerId);
        if (doer.getServerRoleMap().get(serverId).getAbilities().contains(Ability.REMOVE_SERVER_MEMBER)) {
            Person member = serverInformation.getPersons().get(personId);
            member.getServerIdList().remove(serverId);
            member.getServerRoleMap().remove(serverId);
            return true;
        } else
            throw new MissingAbilityException("missing remove server member ability");
    }

    @Override
    public boolean setRole(String doerId, String serverId, String personId, ArrayList<Ability> abilities) throws MissingAbilityException {
        DiscordServer server = serverInformation.getServers().get(serverId);
        if (server.getAdmin().getId().equals(doerId)) {
            Person person = server.getPersons().get(personId);
            Role role = new Role();
            role.setAbilities(abilities);
            person.getServerRoleMap().replace(serverId, role);
            server.getPersonRoleMap().replace(person.getId(), role);
            return true;
        } else
            throw new MissingAbilityException("missing server admin ability");
    }

    @Override
    public boolean limitChannels(String personId, String serverId, String doerId, List<String> limitedChannelIds) throws MissingAbilityException {
        Person person = serverInformation.getPersons().get(personId);
        DiscordServer server = serverInformation.getServers().get(serverId);
        Person doer = server.getPersons().get(doerId);
        if (server.getPersonRoleMap().get(doer).getAbilities().contains(Ability.LIMIT_CHANNEL_MEMBER)) {
            for (String s : limitedChannelIds) {
                person.getServerChannelMap().get(serverId).remove(s);
            }
            return true;
        } else
            throw new MissingAbilityException("missing limit channel member ability");
    }
}
