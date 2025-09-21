package com.messenger.whatsappclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Internal DB PK

    @Column(name = "chat_id", nullable = false, unique = true, updatable = false)
    private String chatId; // Business ID

    private String chatName;

    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> participants = new HashSet<>();

    //Auto-assign UUID when a new chat is created
    @PrePersist
    public void generateChatId(){
        if (this.chatId == null){
            this.chatId = UUID.randomUUID().toString();
        }
    }
}
