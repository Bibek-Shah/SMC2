package com.contact.smc.Repository;

import com.contact.smc.Entity.Contact;
import com.contact.smc.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

//    List<Contact> findByUser(User user);

    @Query("SELECT c FROM Contact c WHERE c.user.userId = :userId")
    List<Contact> findByUserId(String userId);

    Page<Contact> findByUser(User user, Pageable pageable);

    Page<Contact> findAllByUserAndNameContainingIgnoreCase(User user, String name, Pageable pageable);

    Page<Contact> findAllByUserAndEmailContainingIgnoreCase(User user,String emailKeyword,
                                                     Pageable pageable);

    Page<Contact> findAllByUserAndPhoneNumberContainingIgnoreCase(User user,String phoneKeyword,
                                                           Pageable pageable);


}
