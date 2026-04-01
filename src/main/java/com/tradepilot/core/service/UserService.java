package com.messenger.whatsappclone.service;

import com.messenger.whatsappclone.dto.request.UserRegisterRequest;
import com.messenger.whatsappclone.dto.response.UserStatus;
import com.messenger.whatsappclone.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User registerUser(UserRegisterRequest dto);

    Optional<User> getUser(UUID userId);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    User updateUserStatus(UUID userId, UserStatus userStatus);

    void deleteUser(UUID userId);

    List<User> getAllUsers();

    boolean isUsernameTaken(String username);

    boolean isPhoneNumberTaken(String phoneNumber);
}
