package com.messenger.whatsappclone.controller;

import com.messenger.whatsappclone.dto.SendMessageDto;
import com.messenger.whatsappclone.entity.Message;
import com.messenger.whatsappclone.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageDto dto) {
        Message msg = messageService.sendMessage(dto.getChatId(), dto.getSenderId(), dto.getContent());
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageService.getMessagesByChat(chatId));
    }
}
