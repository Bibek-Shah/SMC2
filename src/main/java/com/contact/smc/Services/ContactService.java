package com.contact.smc.Services;

import com.contact.smc.Entity.Contact;
import com.contact.smc.Entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    Contact addContactDetails(Contact contact);

    Contact getContactById(String contactId);

    void deleteUser(String contactId);

    List<Contact> findByUserId(String userId);
    Page<Contact> getByUser(User user,int page, int size,String sortBy,String sortDir);
    Page<Contact>   searchByName(String nameKeyword,int page, int size,String sortBy,String sortDir,User user);
    Page<Contact>   searchByEmail(String emailKeyword,int page, int size,String sortBy,String sortDir,User user);
    Page<Contact>   searchByPhone(String phoneKeyword,int page, int size,String sortBy,String sortDir,User user);

}
