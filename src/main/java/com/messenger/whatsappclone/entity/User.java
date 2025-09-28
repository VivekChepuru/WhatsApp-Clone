package com.messenger.whatsappclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.messenger.whatsappclone.dto.response.UserStatus;
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
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String userId;  // External UUID for APIs

    @Column(nullable = false)
    private String username;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING) // store as "ONLINE" or "OFFLINE
    @Column(nullable = false)
    private UserStatus userStatus; // default OFFLINE when user registers

    // Inverse side of ManyToMany (no @JoinTable here)
    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private Set<Chat> chats = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }

        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString(); // generate external userId
        }

        if (this.userStatus == null) {
            this.userStatus = UserStatus.OFFLINE; // default value
        }
    }
}
