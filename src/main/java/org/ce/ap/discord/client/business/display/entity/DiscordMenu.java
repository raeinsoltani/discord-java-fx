package org.ce.ap.discord.client.business.display.entity;

import java.util.HashMap;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class DiscordMenu extends Menu {

    public DiscordMenu() {
        super(new HashMap<>());
        this.title = "Discord Client <><> Menu";
        ITEMS.put("--logOut", "Logout from Server");
        ITEMS.put("--chpwd", "Change password");
        ITEMS.put("--chimg", "Change person image");
        ITEMS.put("--shfriends", "Show friend List (those has private chat with you)");
        ITEMS.put("--shblocks", "Show blocked users list");
        ITEMS.put("--block <id>", "Block a person, <id> is user id");
        ITEMS.put("--unblock <id>", "Unblock a person, <id> is user id");
        ITEMS.put("--shfriendreq", "Show friend requests");
        ITEMS.put("--acpfriendreq <id>", "Accept friend  request , <id> is the user's id");
        ITEMS.put("--rejfriendreq <id>", "Reject friend  request , <id> is the user's id");
        ITEMS.put("--sndfirendreq <id>", "Send friend request to <id>");
        ITEMS.put("--shchat", "Show private chat list");
        ITEMS.put("--selchat <id>", "Select a chat, <id> is chat id");
        ITEMS.put("--shservers", "Show server list");
        ITEMS.put("--selserver <id>", "Select a Server, <id> is server id");
        ITEMS.put("--addserver", "creat a discord server");
    }
}
