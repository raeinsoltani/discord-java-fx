package org.ce.ap.discord.common.entity.api;

import org.ce.ap.discord.common.entity.api.enumeration.HeaderType;

import java.io.Serializable;

/**
 * @author Parham Ahmady
 * @since 6/1/2022
 */
public class GeneralDto<T> implements Serializable {

    private static final long serialVersionUID = 8357960960795185082L;

    private final HeaderType header;
    private final T payload;

    public GeneralDto(HeaderType header, T payload) {
        this.header = header;
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public HeaderType getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "{Header: " +
                header.type +
                " , " +
                "payload: " +
                payload
                + "}";
    }
}
