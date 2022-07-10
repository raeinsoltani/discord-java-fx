package org.ce.ap.discord.server.business.authentication;

import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.authentication.AuthenticationInformationDto;
import org.ce.ap.discord.server.business.authentication.exceptions.*;

/**
 * @author Parham Ahmady
 * @since 5/30/2022
 */
public interface AuthenticationService {

    Person register(AuthenticationInformationDto information) throws IdNotUnique, InvalidInputException;

    Person logIn(AuthenticationInformationDto information) throws InvalidUserNamePatternException, InvalidInputException, WrongPasswordException, UsernameNotFoundException;

    /**
     * @param information of the user
     * @return true if pattern was right
     */
    default boolean checkPattern(AuthenticationInformationDto information) throws InvalidInputException {
        return false;
    }
}
