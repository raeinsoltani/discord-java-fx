package org.ce.ap.discord.server.business.authentication.exceptions;

/**
 * @author Arian Qazvini
 */
public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException(String errMsg)
    {
        super(errMsg);
    }
}
