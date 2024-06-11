package com.contact.smc.Helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Objects;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    public static String getEmailOfLoggedInUser(Authentication authentication) {


        if (authentication instanceof OAuth2AuthenticationToken aOAuth2AuthenticationToken) {

            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {

                // sign with google
                System.out.println("Getting email from google");
                username = Objects.requireNonNull(oauth2User.getAttribute("email")).toString();

            } else if (clientId.equalsIgnoreCase("github")) {

                // sign with github
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";

            } else {
                System.out.println("Getting email from local database");
                username = authentication.getName();
            }
            log.info("Logged in userName from helper: {}", username);
            return username;


        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }

    }
}
