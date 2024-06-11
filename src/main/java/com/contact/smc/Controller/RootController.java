package com.contact.smc.Controller;


import com.contact.smc.Entity.User;
import com.contact.smc.Helper.Helper;
import com.contact.smc.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class RootController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(RootController.class.getName());


    @ModelAttribute
    void addLoggedInUser(Authentication authentication, Model model) {

        if (authentication == null) {
            logger.debug("No authentication available");
            return;
        }
        String userName = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Logged in userName: {}", userName);

        User user = userService.getUserByEmail(userName);

        if (user == null) {
            logger.debug("No user found with email: " + userName);
            model.addAttribute("loggedInUser", null);

        } else {
            logger.debug("User found: " + user.getEmail());
            model.addAttribute("loggedInUser", user);

        }
    }
}



