package com.contact.smc.Services.Impl;


import com.contact.smc.Entity.User;
import com.contact.smc.Helper.AppConstant;
import com.contact.smc.Repository.UserRepository;
import com.contact.smc.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User createUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(List.of(AppConstant.ROLE_USER));

        user.setProfilePic("https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50");
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(
                        () -> new RuntimeException("User Not Found")
                );
        existingUser.setFullName(user.getFullName());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNo(user.getPhoneNo());
        existingUser.setAbout(user.getAbout());
        existingUser.setProfilePic(user.getProfilePic());

        existingUser.setEmailVerified(user.isEmailVerified());
        existingUser.setPhoneVerified(user.isPhoneVerified());
        existingUser.setProvider(user.getProvider());
        existingUser.setProviderUserId(user.getProviderUserId());

        User updatedUser = userRepository.save(existingUser);
        return Optional.of(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(
                        () -> new RuntimeException("User Not Found")
                );
        userRepository.delete(existingUser);
    }

    @Override
    public boolean isUserExist(String userId) {
        User existingUser = userRepository.findById(userId)
                .orElse(null);
        /*  return existingUser != null ? true : false;*/
        return existingUser != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
