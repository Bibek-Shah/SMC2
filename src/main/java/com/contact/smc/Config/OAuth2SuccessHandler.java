package com.contact.smc.Config;

import com.contact.smc.Entity.Providers;
import com.contact.smc.Entity.User;
import com.contact.smc.Helper.AppConstant;
import com.contact.smc.Repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication success: {}", authentication.getName());


        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String clientRegistrationId = auth2AuthenticationToken.getAuthorizedClientRegistrationId();

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        oAuth2User.getAttributes().forEach((key, value) -> {
            logger.info("key: {}, value: {}", key, value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEnabled(true);
        user.setRoles(List.of(AppConstant.ROLE_USER));
        user.setEmailVerified(true);

        if (clientRegistrationId.equalsIgnoreCase("google")) {

            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setFullName(oAuth2User.getAttribute("name").toString());
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(user.getName());
            user.setProfilePic(Objects.requireNonNull(oAuth2User.getAttribute("picture")).toString());
            user.setAbout("Register From Google");


        } else if (clientRegistrationId.equalsIgnoreCase("github")) {
            String email = oAuth2User.getAttribute("email") != null ?
                    oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString() + "@github.com";
            user.setProfilePic(oAuth2User.getAttribute("avatar_url").toString());
            user.setName(oAuth2User.getAttribute("login").toString());
            user.setProviderUserId(oAuth2User.getName());
            user.setEmail(email);
            user.setProvider(Providers.GITHUB);
            user.setAbout("Register From Github");
        } else if (clientRegistrationId.equalsIgnoreCase("facebook")) {

            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setFullName(oAuth2User.getAttribute("name").toString());
            user.setProvider(Providers.FACEBOOK);
            user.setProviderUserId(oAuth2User.getAttribute("id").toString());
            user.setProfilePic(Objects.requireNonNull(oAuth2User.getAttribute("picture")).toString());
            user.setAbout("Register From Facebook");

        } else {
            logger.info("Login with {} not supported", clientRegistrationId);
            return;
        }

        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (existingUser == null) {
            userRepository.save(user);
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
