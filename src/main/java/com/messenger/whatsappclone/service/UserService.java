package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);

    User getUserById(Long id);

    User getUserByPhoneNumber(String phoneNumber);

    User updateUserStatus(Long id, boolean online);

    void deleteUser(Long id);

    List<User> getAllUsers();
}
