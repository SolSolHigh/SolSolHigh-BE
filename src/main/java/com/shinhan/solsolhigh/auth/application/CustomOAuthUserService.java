package com.shinhan.solsolhigh.auth.application;

import com.shinhan.solsolhigh.session.config.SessionConstants;
import com.shinhan.solsolhigh.user.domain.*;
import com.shinhan.solsolhigh.user.exception.UserSignupNotCompletedException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final TemporaryUserRepository temporaryUserRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuthResponse response = switch (registrationId) {
            case "naver" -> NaverOAuthResponse.from(oAuth2User.getAttributes());
            case "kakao" -> KakaoOAuthResponse.from(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("Auth error");
        };

        Optional<User> userOpt = userRepository.findByEmail(response.getEmail());
        Optional<TemporaryUser> temporaryUserOpt = temporaryUserRepository.findByEmail(response.getEmail());

        if(userOpt.isEmpty() && temporaryUserOpt.isEmpty()) {
            TemporaryUser newTemporaryUser = TemporaryUser.builder().email(response.getEmail()).name(response.getName()).build();
            temporaryUserRepository.save(newTemporaryUser);
            throw new OAuth2AuthenticationException(new OAuth2Error("User signup not completed"), new UserSignupNotCompletedException());
        }

        User user = userOpt.orElseThrow(() -> new OAuth2AuthenticationException(new OAuth2Error("User signup not completed"), new UserSignupNotCompletedException()));

        httpSession.setAttribute(SessionConstants.LOGIN_USER.name(), user.getId());

        return UserPrinciple.builder()
                .id(user.getId())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .attributes(oAuth2User.getAttributes())
                .type(user.getType())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
