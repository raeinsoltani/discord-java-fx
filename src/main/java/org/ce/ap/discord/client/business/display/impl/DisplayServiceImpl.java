package org.ce.ap.discord.client.business.display.impl;

import org.ce.ap.discord.client.business.display.DisplayService;
import org.ce.ap.discord.client.business.display.entity.*;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class DisplayServiceImpl implements DisplayService {
    private final MainMenu mainMenu = new MainMenu();
    private final DiscordMenu discordMenu = new DiscordMenu();
    private Menu currentMenu;

    public DisplayServiceImpl(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    @Override
    public void printCurrentMenu() {
        System.out.println(currentMenu);
    }

    @Override
    public void printMainMenu() {
        currentMenu = mainMenu;
        System.out.println(mainMenu);
    }

    @Override
    public void printDiscordMenu() {
        currentMenu = discordMenu;
        System.out.println(discordMenu);
    }

    @Override
    public void printPrivateChatMenu(PrivateChatMenu menu) {
        currentMenu = menu;
        System.out.println(menu);
    }

    @Override
    public void printServerMenu(ServerMenu menu) {
        currentMenu = menu;
        System.out.println(menu);
    }

    @Override
    public void printTextChannelMenu(TextChannelMenu menu) {
        currentMenu = menu;
        System.out.println(menu);
    }
}
