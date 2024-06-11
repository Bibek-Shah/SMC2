package com.contact.smc.Controller;

import ch.qos.logback.core.model.Model;
import com.contact.smc.Entity.User;
import com.contact.smc.Form.UserForm;
import com.contact.smc.Helper.Message;
import com.contact.smc.Helper.MessageType;
import com.contact.smc.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FormController {


    private static final Logger logger = LoggerFactory.getLogger(FormController.class);
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/do-register")
    public String registerUser(@Valid @ModelAttribute UserForm userForm, BindingResult result,
                               Model model, HttpSession session) {
        if (result.hasErrors()) {
            logger.info("Registration error: {}", result.getAllErrors());
            return "register";
        }
        try {
            User user = modelMapper.map(userForm, User.class);
            User registeredUser = this.userService.createUser(user);
            logger.info("New user registered: {}", registeredUser);
            Message message = Message.builder()
                    .content("User registration successful")
                    .type(MessageType.GREEN).build();

            session.setAttribute("message", message);
            return "redirect:/register";
        } catch (Exception e) {
            logger.error("Error in registration: {}", e.getMessage());
            e.printStackTrace();
            return "redirect:/register";
        }

    }
}
