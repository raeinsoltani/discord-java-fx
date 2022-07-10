package org.ce.ap.discord.client.business.display.entity;

import org.ce.ap.discord.common.util.ToStringHelper;

import java.util.Map;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class Menu {
    protected final Map<String, String> ITEMS;
    protected String title;

    public Menu(Map<String, String> items) {
        ITEMS = items;
    }

    @Override
    public String toString() {
        return ToStringHelper.makeMenuString(ITEMS, title);
    }
}
