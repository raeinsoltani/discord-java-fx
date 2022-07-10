package org.ce.ap.discord.common.util;

import java.util.Map;

/**
 * @author Parham Ahmady
 * @since 6/27/2022
 */
public class ToStringHelper {

    public static String makeMenuString(Map<String, String> items, String title) {
        StringBuilder builder = new StringBuilder("<><>" + title + "<><>");
        builder.append("\n");
        for (String item : items.keySet())
            builder.append(item).append(" : ").append(items.get(item)).append("\n");
        return builder.toString();
    }
}
