package com.shinhan.solsolhigh.auth.application;

import com.shinhan.solsolhigh.session.config.SessionConstants;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserRepository;
import com.shinhan.solsolhigh.user.exception.UserSignupNotCompletedException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuthResponse response = switch (registrationId) {
            case "naver" -> NaverOAuthResponse.from(oAuth2User.getAttributes());
            case "kakao" -> KakaoOAuthResponse.from(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("Auth Error");
        };

        Optional<User> userOptional = userRepository.findByEmail(response.getEmail());

        if(userOptional.isEmpty()) {
            User newUser = User.builder().email(response.getEmail()).name(response.getName()).build();
            userRepository.save(newUser);
            throw new UserSignupNotCompletedException();
        }

        User user = userOptional.get();
        if(!user.getIsSignUpCompleted())
            throw new UserSignupNotCompletedException();

        httpSession.setAttribute(SessionConstants.LOGIN_USER.name(), user.getId());

        String userNameAttributeName = request.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }
}
