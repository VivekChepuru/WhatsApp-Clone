package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;
import com.messenger.whatsappclone.service.ChatService;
import com.messenger.whatsappclone.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(
            @RequestParam String senderUserId,
            @RequestParam String chatId,
            @RequestParam String content) {

        Message message = messageService.sendMessage(
                UUID.fromString(senderUserId),
                UUID.fromString(chatId),
                content);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable UUID chatId) {
        Chat chat = chatService.getChatByChatId(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found with ID: " + chatId));

        return ResponseEntity.ok(messageService.getMessagesByChat(chat));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID messageId) {
        return messageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with ID: " + messageId));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(UUID.fromString(messageId));
        return ResponseEntity.noContent().build();
    }
}
