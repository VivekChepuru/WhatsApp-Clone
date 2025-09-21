package com.messenger.whatsappclone.entity;

import com.messenger.whatsappclone.dto.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // Internal DB primary key

    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String userId;  // External UUID for APIs

    @Column(nullable = false)
    private String username;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // store as "ONLINE" or "OFFLINE
    @Column(nullable = false)
    private UserStatus userStatus; // default OFFLINE when user registers

    @PrePersist
    public void generateUserId() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString();
        }
        if (this.userStatus == null) {
            this.userStatus = UserStatus.OFFLINE; // Default status
        }
    }
}
