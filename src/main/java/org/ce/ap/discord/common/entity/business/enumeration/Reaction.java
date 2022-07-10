package org.ce.ap.discord.common.entity.business.enumeration;

import org.ce.ap.discord.server.business.discord.exceptions.InvalidIdException;

public enum Reaction {
    LIKE(1),
    DISLIKE(2),
    LAUGH(3);
    private final int index;
    Reaction(int index){
        this.index=index;
    }

    public static Reaction getByIndex(int index) throws InvalidIdException {
        if (index==1)
            return LIKE;
        else if (index==2)
            return DISLIKE;
        else if (index==3)
            return LAUGH;
        else
            throw new InvalidIdException("invalid index");
    }
}
