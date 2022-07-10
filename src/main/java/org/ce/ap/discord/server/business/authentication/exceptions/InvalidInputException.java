package org.ce.ap.discord.server.business.authentication.exceptions;

/**
 * @author Arian Qazvini
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String errMsg) {
        super(errMsg);
    }
}
