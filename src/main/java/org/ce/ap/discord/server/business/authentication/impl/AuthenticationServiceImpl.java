package org.ce.ap.discord.server.business.authentication.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.ServerInformation;
import org.ce.ap.discord.common.entity.business.authentication.AuthenticationInformationDto;
import org.ce.ap.discord.server.business.authentication.AuthenticationService;
import org.ce.ap.discord.server.business.authentication.exceptions.IdNotUnique;
import org.ce.ap.discord.server.business.authentication.exceptions.InvalidInputException;
import org.ce.ap.discord.server.business.authentication.exceptions.UsernameNotFoundException;
import org.ce.ap.discord.server.business.authentication.exceptions.WrongPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arian Qazvini
 * @since 5/30/2022
 */
public class AuthenticationServiceImpl implements AuthenticationService, InitializingBean {
    private ServerInformation serverInformation;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        serverInformation = (ServerInformation) context.getApplicationBeans().get(ServerInformation.class);
    }

    @Override
    public Person register(AuthenticationInformationDto information) throws IdNotUnique, InvalidInputException {
        if (serverInformation.getPersons().containsKey(information.getId())) {
            throw new IdNotUnique("This username already exists");
        } else if (checkPattern(information)) {
            Person person = new Person(information.getId(), information.getName(), information.getPassword(), information.getEmail(), information.getPhoneNumber(), information.getPhoto());
            serverInformation.getPersons().put(information.getId(), person);
            return person;
        }
        return null;
    }

    @Override
    public Person logIn(AuthenticationInformationDto information) throws WrongPasswordException, UsernameNotFoundException {
        if (serverInformation.getPersons().containsKey(information.getId())) {
            if (serverInformation.getPersons().get(information.getId()).getPassword().equals(information.getPassword())) {
                return serverInformation.getPersons().get(information.getId());
            } else {
                throw new WrongPasswordException("Wrong Password");
            }
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    @Override
    public boolean checkPattern(AuthenticationInformationDto information) throws InvalidInputException {
        if (information.getId().length() < 6) {
            throw new InvalidInputException("Username must contain at least 6 characters");
        }
        if (information.getPassword().length() < 8) {
            throw new InvalidInputException("Password must contain at least 8 characters");
        }
        if (!isLetterOrDigit(information.getId())) {
            throw new InvalidInputException("Username must contain only digits or letters");
        }
        if (!isLetterOrDigit(information.getPassword())) {
            throw new InvalidInputException("Password must contain only digits or letters");
        }
        if (!containsLowerCase(information.getPassword())) {
            throw new InvalidInputException("Password must contain lowercase letters");
        }
        if (!containsUpperCase(information.getPassword())) {
            throw new InvalidInputException("Password must contain uppercase letters");
        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(information.getEmail());
        if (!matcher.matches()) {
            throw new InvalidInputException("Email pattern is not correct");
        }
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher matcher1 = p.matcher(information.getPhoneNumber());
        if (!matcher1.matches()) {
            throw new InvalidInputException("Phone number pattern is not correct");
        }
        return true;
    }

    private boolean isLetterOrDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean containsLowerCase(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                flag = true;
            }
        }
        return flag;
    }

    private boolean containsUpperCase(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                flag = true;
            }
        }
        return flag;
    }
}
