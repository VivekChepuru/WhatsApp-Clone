package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.dto.request.ChatCreateRequest;
import com.messenger.whatsappclone.dto.response.ChatResponse;
import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;
    //TODO: Need to test
    @PostMapping
    public ResponseEntity<ChatResponse> createChat(@Valid @RequestBody ChatCreateRequest req) {
        Chat chat = chatService.createChat(req.getChatName(), req.getIsGroup(), req.getParticipantIds());

        ChatResponse resp = toResponse(chat);
        return ResponseEntity.status(201).body(resp);
    }

    private ChatResponse toResponse(Chat chat) {
        ChatResponse r = new ChatResponse();
        r.setId(chat.getId());
        r.setChatName(chat.getChatName());
        r.setGroup(chat.isGroup());
        r.setParticipants(chat.getParticipants().stream().map(u -> {
            ChatResponse.ParticipantDto p = new ChatResponse.ParticipantDto();
            p.setUserId(UUID.fromString(u.getUserId()));
            p.setUsername(u.getUsername());
            p.setPhoneNumber(u.getPhoneNumber());
            return p;
        }).collect(Collectors.toList()));
        return r;
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> getChatById(@PathVariable UUID chatId) {
        Chat chat = chatService.getChat(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found with ID: " + chatId));
        return ResponseEntity.ok(toResponse(chat));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAllChats() {
        List<ChatResponse> chats = chatService.getAllChats()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(chats);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{chatId}/add-user/{userId}")
    public ResponseEntity<ChatResponse> addUserToChat(@PathVariable UUID chatId, @PathVariable String userId) {
        Chat chat = chatService.addUserToChat(chatId, userId);
        return ResponseEntity.ok(toResponse(chat));
    }

    @PostMapping("/{chatId}/remove-user/{userId}")
    public ResponseEntity<ChatResponse> removeUserFromChat(@PathVariable UUID chatId, @PathVariable String userId) {
        Chat chat = chatService.removeUserFromChat(chatId, userId);
        return ResponseEntity.ok(toResponse(chat));
    }
}
