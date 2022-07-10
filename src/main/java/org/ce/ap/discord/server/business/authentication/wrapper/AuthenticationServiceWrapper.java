package org.ce.ap.discord.server.business.authentication.wrapper;

import org.ce.ap.discord.common.entity.api.dto.authentication.*;

/**
 * @author Parham Ahmady
 * @since 6/26/2022
 */
public interface AuthenticationServiceWrapper {

    LoginResponseDto logInUser(LoginRequestDto requestDto);

    LogoutResponseDto logOutUser(LogoutRequestDto requestDto);

    RegisterResponseDto registerUser(RegisterRequestDto requestDto);

    boolean isLogin(String id);
}
