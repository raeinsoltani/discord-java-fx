package org.ce.ap.discord.client.business.display.entity;

import java.util.HashMap;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class MainMenu extends Menu {
    public MainMenu() {
        super(new HashMap<>());
        this.title = "Main Menu";
        ITEMS.put("--login", "Login to Discord server");
        ITEMS.put("--register", "Register to discord");
        ITEMS.put("--exit", "will exit from application (log out in case of active use)");
    }
}
