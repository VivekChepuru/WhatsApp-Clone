package com.messenger.whatsappclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Internal DB PK

    @Column(name = "message_id", nullable = false, unique = true, updatable = false)
    private String messageId; // Business UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    public void generateMessageId() {
        if (this.messageId == null) {
            this.messageId = UUID.randomUUID().toString();
        }
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

}
