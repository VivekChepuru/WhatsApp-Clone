package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.User;
import com.messenger.whatsappclone.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> createChat(
            @RequestParam String chatName,
            @RequestParam boolean isGroup,
            @RequestBody List<User> participants) {
        return ResponseEntity.ok(chatService.createChat(chatName, isGroup, participants));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getChatById(id));
    }

    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok("Chat with ID " + id + " deleted successfully.");
    }

    @PostMapping("/{chatId}/add-user")
    public ResponseEntity<Chat> addUserToChat(@PathVariable Long chatId, @RequestBody User user) {
        return ResponseEntity.ok(chatService.addUserToChat(chatId, user));
    }

    @PostMapping("/{chatId}/remove-user")
    public ResponseEntity<Chat> removeUserFromChat(@PathVariable Long chatId, @RequestBody User user) {
        return ResponseEntity.ok(chatService.removeUserFromChat(chatId, user));
    }

}
