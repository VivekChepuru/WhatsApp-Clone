package com.messenger.whatsappclone.service.implementation;

import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.repository.ChatRepository;
import com.messenger.whatsappclone.repository.MessageRepository;
import com.messenger.whatsappclone.repository.UserRepository;
import com.messenger.whatsappclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public User registerUser(User user) {
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number. Must be 10 digits.");
        }

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered.");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User with ID " +id+ " not found."));
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new IllegalArgumentException("User with phone number " +phoneNumber+ " not found."));
    }

    @Override
    public User updateUserStatus(Long id, boolean online) {
        User user = getUserById(id);
        user.setOnline(online);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
