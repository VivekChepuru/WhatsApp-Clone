package com.messenger.whatsappclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();    // UUID PK

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    //Owing side of *to*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private Set<User> participants = new HashSet<>();

    //Auto-assign UUID when a new chat is created
    @PrePersist
    public void prePersist(){
        if (this.id == null){
            this.id = UUID.randomUUID();
        }
    }
}
