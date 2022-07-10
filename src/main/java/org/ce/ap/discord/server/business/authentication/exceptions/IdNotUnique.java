package org.ce.ap.discord.server.business.authentication.exceptions;

/**
 * @author Arian Qazvini
 */
public class IdNotUnique extends Exception {
    public IdNotUnique(String errMsg) {
        super(errMsg);
    }
}
