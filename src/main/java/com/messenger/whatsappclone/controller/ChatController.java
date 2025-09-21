package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> createChat(
            @RequestParam String chatName,
            @RequestParam boolean isGroup,
            @RequestBody List<UUID> participantIds) {
        Chat chat = chatService.createChat(chatName, isGroup, participantIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(chat);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChatById(@PathVariable UUID chatId) {
        return ResponseEntity.ok(chatService.getChatByChatId(chatId).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{chatId}/add-user/{userId}")
    public ResponseEntity<Chat> addUserToChat(@PathVariable UUID chatId, @PathVariable UUID userId) {
        return ResponseEntity.ok(chatService.addUserToChat(chatId, userId));
    }

    @PostMapping("/{chatId}/remove-user/{userId}")
    public ResponseEntity<Chat> removeUserFromChat(@PathVariable UUID chatId, @PathVariable UUID userId) {
        return ResponseEntity.ok(chatService.removeUserFromChat(chatId, userId));
    }

}
