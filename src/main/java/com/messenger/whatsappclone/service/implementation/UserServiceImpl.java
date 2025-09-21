package com.messenger.whatsappclone.service.implementation;

import com.messenger.whatsappclone.dto.RegisterUserRequest;
import com.messenger.whatsappclone.dto.UserStatus;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.repository.ChatRepository;
import com.messenger.whatsappclone.repository.MessageRepository;
import com.messenger.whatsappclone.repository.UserRepository;
import com.messenger.whatsappclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(RegisterUserRequest dto) {
        if (dto.getPhoneNumber() == null || !dto.getPhoneNumber().matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number. Must be 10 digits.");
        }

        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered.");
        }
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(dto.getUsername().trim());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword()); // TODO: hash this before production!
        user.setUserStatus(UserStatus.OFFLINE); // enforce system-controlled value

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUserId(UUID userId) {
        return Optional.ofNullable(userRepository.findByUserId(userId.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found.")));
    }

    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow((() -> new IllegalArgumentException("User not found."))));
    }

    @Override
    public User updateUserStatus(UUID userId, UserStatus userStatus) {
        User user = userRepository.findByUserId(userId.toString())
                        .orElseThrow(()-> new IllegalArgumentException("User with ID " + userId + " not found."));
        user.setUserStatus(userStatus);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        User user = userRepository.findByUserId(userId.toString())
                        .orElseThrow(()-> new IllegalArgumentException("User with ID " + userId + " not found."));
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isPhoneNumberTaken(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}
