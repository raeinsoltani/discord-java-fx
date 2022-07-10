package org.ce.ap.discord.client.business.display.entity;

import java.util.HashMap;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class PrivateChatMenu extends Menu {

    public PrivateChatMenu(String id) {
        super(new HashMap<>());
        this.title = "Chat Menu <> Chat_ID : " + id;
        ITEMS.put("--shmessages <contactId>", "Show all messages");
        ITEMS.put("--sndtxtmessage <message>", "send text message");
        ITEMS.put("--sndfilemessage <file_address>", "send file message");
    }
}
