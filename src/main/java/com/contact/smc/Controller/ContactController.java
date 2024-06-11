package com.contact.smc.Controller;

import com.contact.smc.Entity.Contact;
import com.contact.smc.Entity.User;
import com.contact.smc.Form.ContactForm;
import com.contact.smc.Form.ContactSearchForm;
import com.contact.smc.Helper.AppConstant;
import com.contact.smc.Helper.Helper;
import com.contact.smc.Helper.Message;
import com.contact.smc.Helper.MessageType;
import com.contact.smc.Services.ContactService;
import com.contact.smc.Services.ImageService;
import com.contact.smc.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/contacts")
public class ContactController {

    private final ModelMapper modelMapper;
    private final ContactService contactService;
    private final ImageService imageService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/add")
    public String getContactPage(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContactDetails(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
                                     Model model, Authentication authentication, HttpSession session) {

        if (result.hasErrors()) {
            Message message = Message.builder()
                    .content("Invalid Contact Details")
                    .type(MessageType.RED).build();
            logger.info("Invalid Contact Details: {}", result.getAllErrors());
            session.setAttribute("message", message);
            return "user/add_contact";
        }

        String userName = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("ContactImage : {}", contactForm.getContactImage().getOriginalFilename());
        User user = userService.getUserByEmail(userName);

        String fileUrl = imageService.uploadImage(contactForm.getContactImage());
        Contact contact = this.modelMapper.map(contactForm, Contact.class);
        contact.setPicture(contactForm.getContactImage().getOriginalFilename());
        contact.setUser(user);
        contact.setPicture(fileUrl);
        contact.setCloudinaryImagePublicId(fileUrl);
        System.out.println(contact);
        Contact addContactDetails = contactService.addContactDetails(contact);
        logger.info("New contact added: {}", addContactDetails);
        this.modelMapper.map(addContactDetails, contactForm);
        return "redirect:/user/contacts/add";
    }


    /*View Contact Page*/
    @GetMapping
    public String viewContact(Model model, Authentication authentication,
                              @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortDir) {

        String userName = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(userName);

        Page<Contact> contacts = contactService.getByUser(user, page, size, sortBy, sortDir);
        model.addAttribute("contacts", contacts);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contact";
    }

    @GetMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId) {
        contactService.deleteUser(contactId);
        return "redirect:/user/contacts";
    }

    @GetMapping("/search")
    public String searchHandler(ContactSearchForm contactSearchForm,
                                @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortDir,
                                Model model,
                                Authentication authentication) {

        logger.info("Field: {}, Keyword: {}", contactSearchForm.getField(), contactSearchForm.getKeyword());
        User user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        model.addAttribute("user", user);


        Page<Contact> pageContacts = null;
        if (contactSearchForm.getField().equals("name")) {
            pageContacts = contactService.searchByName(contactSearchForm.getKeyword(), page, size, sortBy, sortDir,user);

        } else if (contactSearchForm.getField().equals("email")) {
            pageContacts = contactService.searchByEmail(contactSearchForm.getKeyword(), page, size, sortBy, sortDir,user);

        } else if (contactSearchForm.getField().equals("phone")) {
            pageContacts = contactService.searchByPhone(contactSearchForm.getKeyword(), page, size, sortBy, sortDir,user);
        }
        logger.info("Page Contacts: {}", pageContacts);
        model.addAttribute("contacts", pageContacts);
        model.addAttribute("contactSearchForm", contactSearchForm);


        return "user/search";
    }
}


