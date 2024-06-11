package com.contact.smc.Controller;

import com.contact.smc.Form.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({"/", "/home"})
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }

    @GetMapping("/services")
    public String getServicesPage() {
        return "services";
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        UserForm user = new UserForm();
        model.addAttribute("userForm", user);
        return "register";
    }

}
