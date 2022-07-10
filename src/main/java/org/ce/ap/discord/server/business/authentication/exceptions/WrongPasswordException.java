package org.ce.ap.discord.server.business.authentication.exceptions;

/**
 * @author Arian Qazvini
 */
public class WrongPasswordException extends Exception{
    public WrongPasswordException(String errMsg)
    {
        super(errMsg);
    }
}
