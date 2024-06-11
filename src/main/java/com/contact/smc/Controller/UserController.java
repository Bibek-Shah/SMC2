package com.contact.smc.Controller;

import com.contact.smc.Helper.Helper;
import com.contact.smc.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    Logger logger = Logger.getLogger(UserController.class.getName());


    @GetMapping("/dashboard")
    public String getUserDashboard(Authentication authentication) {
        String userName = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User name: " + userName);
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String getUserProfile(Authentication authentication, Model model) {


        return "user/profile";
    }


}
