package org.ce.ap.discord.common.entity.business.enumeration;

import java.io.Serializable;

public enum ChannelType implements Serializable {
    VOICE("voice"), TEXT("channel");

    private final String name;

    ChannelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
