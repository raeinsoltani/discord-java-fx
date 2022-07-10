package org.ce.ap.discord.common.entity.business.enumeration;

import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;

import java.io.Serializable;
import java.util.ArrayList;

public enum Ability implements Serializable {
    CREAT_CHANNEL(1),
    REMOVE_CHANNEL(2),
    REMOVE_SERVER_MEMBER(3),
    LIMIT_CHANNEL_MEMBER(4),
    LIMIT_SERVER_MEMBER(5),
    RENAME_SERVER(6),
    VISIBLE_CHAT_HISTORY(7),
    PIN_CHANNEL_MESSAGE(8);
    private final int index;
    public static final ArrayList<Ability> allAbilities = new ArrayList<>();

    Ability(int index) {
        this.index = index;
    }

    private static void loadAll() {
        allAbilities.add(CREAT_CHANNEL);
        allAbilities.add(REMOVE_CHANNEL);
        allAbilities.add(REMOVE_SERVER_MEMBER);
        allAbilities.add(LIMIT_CHANNEL_MEMBER);
        allAbilities.add(LIMIT_SERVER_MEMBER);
        allAbilities.add(RENAME_SERVER);
        allAbilities.add(VISIBLE_CHAT_HISTORY);
        allAbilities.add(PIN_CHANNEL_MESSAGE);
    }

    public static ArrayList<Ability> getAllAbilities() {
        loadAll();
        return allAbilities;
    }

    public int getIndex() {
        return index;
    }

    public static Ability getByIndex(int index) throws InvalidIdException {
        if (index == 1)
            return CREAT_CHANNEL;
        else if (index == 2)
            return REMOVE_CHANNEL;
        else if (index == 3)
            return REMOVE_SERVER_MEMBER;
        else if (index == 4)
            return LIMIT_CHANNEL_MEMBER;
        else if (index == 5)
            return LIMIT_SERVER_MEMBER;
        else if (index == 6)
            return RENAME_SERVER;
        else if (index == 7)
            return VISIBLE_CHAT_HISTORY;
        else if (index == 8)
            return PIN_CHANNEL_MESSAGE;
        else throw new InvalidIdException("invalid ability index");
    }
}
