package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.dto.response.MessageResponse;
import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;
import com.messenger.whatsappclone.mapper.MessageMapper;
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

    private MessageService messageService;
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
            @RequestParam String senderUserId,
            @RequestParam String chatId,
            @RequestParam String content) {

        Message message = messageService.sendMessage(
                UUID.fromString(senderUserId),
                UUID.fromString(chatId),
                content);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageMapper.toResponse(message));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponse> getMessageById(@PathVariable UUID messageId) {
        return messageService.getMessage(messageId)
                .map(MessageMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with ID: " + messageId));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByChatId(@PathVariable UUID chatId) {
        Chat chat = chatService.getChat(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found with ID: " + chatId));

        return ResponseEntity.ok(
                messageService.getMessagesByChat(chat).stream()
                        .map(MessageMapper::toResponse)
                        .toList()
        );
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}
