package org.ce.ap.discord.client.business.display.entity;

import org.ce.ap.discord.common.util.ToStringHelper;

import java.util.HashMap;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ServerMenu extends Menu {

    public ServerMenu(String id) {
        super(new HashMap<>());
        this.title = "Server Menu <><> Server ID : " + id;
        ITEMS.put("--addmember <id> <id>", "Add member to Server, <serverId>, <newMemberId>");
        ITEMS.put("--rmvmember <id> <id>", "Remove member from server, <serverId>, <memberId>");
        ITEMS.put("--shmembers", "Show server members");
        ITEMS.put("--shchannels", "Show server channels");
        ITEMS.put("--selchannel <id> <id> <id>", "Select a channel, <serverId>, <categoryId>, <channelId>");
        ITEMS.put("--addvoisch", "Add voice Channel");
        ITEMS.put("--addtxtchannel", "Add text channel");
        ITEMS.put("--rmchannel <id>", "Remove channel");
        ITEMS.put("--setrole <id> <id>","set role, <serverId>, <userId>");
        ITEMS.put("--renameServer <id>","rename server <serverId>");
        ITEMS.put("--react <id> <id> <id>","react, <serverId>, <categoryId>, <channelId>");
        ITEMS.put("--sndchtxtMssg <id> <id> <id>","send message <serverId> <categoryId> <textChannelId>");
        ITEMS.put("--sndchfileMssg <id> <id> <id>","send message <serverId> <categoryId> <textChannelId>");
        //todo add change rule
    }
}
