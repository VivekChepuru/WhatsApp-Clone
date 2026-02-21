package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.dto.request.MessageCreateRequest;
import com.messenger.whatsappclone.dto.response.MessageResponse;
import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.mapper.MessageMapper;
import com.messenger.whatsappclone.repository.UserRepository;
import com.messenger.whatsappclone.service.ChatService;
import com.messenger.whatsappclone.service.MessageService;
import com.messenger.whatsappclone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
            @Valid @RequestBody MessageCreateRequest request,
            Authentication authentication) {

        //Get authenticated user
        String phoneNumber = authentication.getName();
        User sender = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));


        Message message = messageService.sendMessage(
                sender.getId(),
                request.getChatId(),
                request.getContent()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageMapper.toResponse(message));
    }

    //Get all messages in a chat
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByChat(@PathVariable UUID chatId) {
        List<MessageResponse> message = messageService.getMessagesByChat(chatId)
                .stream()
                .map(MessageMapper::toResponse)
                .toList();

        return ResponseEntity.ok(message);
    }

   //Get a single message by ID
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable UUID messageId) {
        Message message = messageService.getMessage(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        return ResponseEntity.ok(MessageMapper.toResponse(message));
    }

    //Delete a message (only sender can delete)
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable UUID messageId,
            Authentication authentication) {

        // Get authenticated user
        String phoneNumber = authentication.getName();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));

        messageService.deleteMessage(messageId, user.getId());

        return ResponseEntity.noContent().build();
    }

    // Edit a message (only sender can edit)
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageResponse> editMessage(
            @PathVariable UUID messageId,
            @RequestParam String newContent,
            Authentication authentication) {

        // Get authenticated user
        String phoneNumber = authentication.getName();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));

        Message message = messageService.editMessage(messageId, user.getId(), newContent);

        return ResponseEntity.ok(MessageMapper.toResponse(message));
    }
}
