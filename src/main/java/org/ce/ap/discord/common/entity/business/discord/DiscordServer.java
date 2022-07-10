package org.ce.ap.discord.common.entity.business.discord;

import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.channel.DiscordChannel;
import org.ce.ap.discord.common.entity.business.discord.channel.TextChannel;
import org.ce.ap.discord.common.entity.business.discord.channel.VoiceChannel;
import org.ce.ap.discord.common.entity.business.enumeration.ChannelType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Negar Anabestani
 * @since 5/30/2022
 */
public class DiscordServer implements Serializable {

    private static final long serialVersionUID = -1305265372952742000L;

    private final UUID id;
    private String name;
    private final Person admin;
    private final Map<String, Role> personRoleMap;
    private final Map<String, Person> persons;
    private Map<String, Category> categories;
    private String defaultTextChannelCatId;
    private String defaultTextChannelId;

    public DiscordServer(String name, Person admin) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.admin = admin;
        loadDefaultCategories();
        personRoleMap = new HashMap<>();
        persons = new HashMap<>();
    }

    public String getDefaultTextChannelCatId() {
        return defaultTextChannelCatId;
    }

    public String getDefaultTextChannelId() {
        return defaultTextChannelId;
    }

    private void loadDefaultCategories() {
        categories = new HashMap<>();
        TextChannel defaultTextChannel = new TextChannel("general");
        defaultTextChannelId = defaultTextChannel.getId().toString();
        Category textChannelCat = new Category("TEXT CHANNELS");
        defaultTextChannelCatId = textChannelCat.getId().toString();
        textChannelCat.addChannel(defaultTextChannel);
        categories.put(textChannelCat.getId().toString(), textChannelCat);
        VoiceChannel defaultVoiceChannel = new VoiceChannel("General");
        Category voiceChannelCat = new Category("VOICE CHANNELS");
        voiceChannelCat.addChannel(defaultVoiceChannel);
        categories.put(voiceChannelCat.getId().toString(), voiceChannelCat);
    }

    public Map<String, Category> getCategories() {
        return categories;
    }

    public Person getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void addCategory(String name) {
        Category category = new Category(name);
        categories.put(category.getId().toString(), category);
    }

    public Map<String, Person> getPersons() {
        return persons;
    }

    public Map<String, Role> getPersonRoleMap() {
        return personRoleMap;
    }

    public void addPerson(Person person, Role role) {
        persons.put(person.getId(), person);
        person.getServerIdList().add(this.id.toString());
        personRoleMap.put(person.getId(), role);
        person.getServerRoleMap().put(this.id.toString(), role);
    }

    public void addChannel(ChannelType type, String categoryId, String name) {
        Category category = categories.get(categoryId);
        DiscordChannel channel = null;
        if (type.equals(ChannelType.TEXT)) {
            channel = new TextChannel(name);
        } else if (type.equals(ChannelType.VOICE)) {
            channel = new VoiceChannel(name);
        }
        if (channel != null)
            category.addChannel(channel);
    }
}
