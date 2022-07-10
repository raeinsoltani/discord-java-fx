package org.ce.ap.discord.client.business.display;

import org.ce.ap.discord.client.business.display.entity.PrivateChatMenu;
import org.ce.ap.discord.client.business.display.entity.ServerMenu;
import org.ce.ap.discord.client.business.display.entity.TextChannelMenu;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public interface DisplayService {

    void printCurrentMenu();

    void printMainMenu();

    void printDiscordMenu();

    void printPrivateChatMenu(PrivateChatMenu menu);

    void printServerMenu(ServerMenu menu);

    void printTextChannelMenu(TextChannelMenu menu);
}
