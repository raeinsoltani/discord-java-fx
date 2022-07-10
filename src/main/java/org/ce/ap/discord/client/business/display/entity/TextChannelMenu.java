package org.ce.ap.discord.client.business.display.entity;

import org.ce.ap.discord.common.util.ToStringHelper;

import java.util.HashMap;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class TextChannelMenu extends Menu {

    public TextChannelMenu(String id) {
        super(new HashMap<>());
        this.title = "Channel Menu <> Channel Id: " + id;
        ITEMS.put("--sndchmssage", "Send a text message in channel");
        ITEMS.put("--pin <index>", "Pin a message");
        ITEMS.put("--react <reaction>", "React to a message (Reactions are: like,dislike,laugh)");
    }

    @Override
    public String toString() {
        return ToStringHelper.makeMenuString(ITEMS, title);
    }
}
