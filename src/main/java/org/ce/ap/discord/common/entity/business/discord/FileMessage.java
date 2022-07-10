package org.ce.ap.discord.common.entity.business.discord;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/28/2022
 */
public class FileMessage implements Serializable {

    private static final long serialVersionUID = 2244681201700466751L;

    private final byte[] file;
    private final String format;

    public FileMessage(byte[] file, String format) {
        this.file = file;
        this.format = format;
    }

    public byte[] getFile() {
        return file;
    }

    public String getFormat() {
        return format;
    }
}
