package com.contact.smc.Services.Impl;

import com.contact.smc.Entity.Contact;
import com.contact.smc.Entity.User;
import com.contact.smc.Repository.ContactRepository;
import com.contact.smc.Services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public Contact addContactDetails(Contact contact) {
        String id = UUID.randomUUID().toString();
        contact.setContactId(id);
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactById(String contactId) {
        return contactRepository.findById(contactId).orElse(null);
    }

    @Override
    public void deleteUser(String contactId) {
        Contact contact = contactRepository.findById(contactId).orElse(null);
        assert contact != null;
        contactRepository.delete(contact);
    }

    @Override
    public List<Contact> findByUserId(String userId) {
        return contactRepository.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir
                .equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int page, int size, String sortBy, String sortDir,User user) {
        Sort sort = sortDir
                .equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findAllByUserAndNameContainingIgnoreCase(user,nameKeyword, pageable);

    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int page, int size, String sortBy, String sortDir,User user) {
        Sort sort = sortDir
                .equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findAllByUserAndEmailContainingIgnoreCase(user,emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhone(String phoneKeyword, int page, int size, String sortBy, String sortDir,User user) {
        Sort sort = sortDir
                .equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepository.findAllByUserAndPhoneNumberContainingIgnoreCase(user,phoneKeyword, pageable);
    }


}
