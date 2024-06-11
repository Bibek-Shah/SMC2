package com.contact.smc.Services;


import com.contact.smc.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> getUserById(String userId);

    Optional<User> updateUser(User user);

    void deleteUser(String userId);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    User getUserByEmail(String email);

    List<User> getAllUsers();

}
