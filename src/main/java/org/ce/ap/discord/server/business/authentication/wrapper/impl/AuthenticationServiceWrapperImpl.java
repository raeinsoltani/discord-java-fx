package org.ce.ap.discord.server.business.authentication.wrapper.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.api.dto.authentication.*;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.authentication.AuthenticationInformationDto;
import org.ce.ap.discord.common.entity.business.enumeration.UserStatus;
import org.ce.ap.discord.server.business.authentication.AuthenticationService;
import org.ce.ap.discord.server.business.authentication.exceptions.*;
import org.ce.ap.discord.server.business.authentication.impl.AuthenticationServiceImpl;
import org.ce.ap.discord.server.business.authentication.wrapper.AuthenticationServiceWrapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public class AuthenticationServiceWrapperImpl implements AuthenticationServiceWrapper, InitializingBean {
    private final Set<Person> loginList;
    private AuthenticationService authenticationService;

    public AuthenticationServiceWrapperImpl() {
        this.loginList = new HashSet<>();
    }

    @Override
    public synchronized LoginResponseDto logInUser(LoginRequestDto requestDto) {
        AuthenticationInformationDto informationDto = makeAuthenticationInformationDto(requestDto);
        boolean successful = false;
        Person result = null;
        String message = null;
        try {
            result = authenticationService.logIn(informationDto);
            loginList.add(result);
            successful = true;
            makeUserOnline(result);
        } catch (InvalidUserNamePatternException | InvalidInputException | WrongPasswordException | UsernameNotFoundException e) {
            message = e.getMessage();
        }
        return new LoginResponseDto(result, successful, message);
    }

    @Override
    public synchronized LogoutResponseDto logOutUser(LogoutRequestDto requestDto) {
        boolean successful = false;
        Iterator<Person> it = loginList.iterator();
        if (requestDto.getPersonId() != null && !requestDto.getPersonId().isBlank()) {
            while (it.hasNext()) {
                Person person = it.next();
                if (person.getId().equals(requestDto.getPersonId())) {
                    successful = true;
                    makeUserOffline(person);
                    break;
                }
            }
        }
        return new LogoutResponseDto(successful);
    }

    @Override
    public synchronized RegisterResponseDto registerUser(RegisterRequestDto requestDto) {
        RegisterResponseDto responseDto = null;
        try {
            Person registeredUser = authenticationService.register(makeAuthenticationInformationDto(requestDto));
            if (registeredUser != null) {
                responseDto = new RegisterResponseDto(registeredUser, true, "");
            } else {
                responseDto = new RegisterResponseDto(null, false, "Register failed");
            }
        } catch (IdNotUnique | InvalidInputException e) {
            responseDto = new RegisterResponseDto(null, false, e.getMessage());
        }
        return responseDto;
    }

    @Override
    public boolean isLogin(String id) {
        if (id == null || id.isBlank())
            return false;
        for (Person person : loginList) {
            if (person.getId().equals(id))
                return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        authenticationService = (AuthenticationService) context.getApplicationBeans().get(AuthenticationServiceImpl.class);
    }

    private void makeUserOnline(Person result) {
        if (result.getUserStatus() == null || result.getUserStatus().equals(UserStatus.OFFLINE))
            result.setUserStatus(UserStatus.ONLINE);
    }

    private void makeUserOffline(Person person) {
        if (person.getUserStatus() == null || person.getUserStatus().equals(UserStatus.ONLINE))
            person.setUserStatus(UserStatus.OFFLINE);
    }

    private AuthenticationInformationDto makeAuthenticationInformationDto(RegisterRequestDto requestDto) {
        return new AuthenticationInformationDto(
                requestDto.getName(), requestDto.getId(),
                requestDto.getPassword(), requestDto.getEmail(), requestDto.getPhoneNumber(), requestDto.getPhoto());
    }

    private AuthenticationInformationDto makeAuthenticationInformationDto(LoginRequestDto requestDto) {
        return new AuthenticationInformationDto(requestDto.getId(), requestDto.getPassword());
    }
}
