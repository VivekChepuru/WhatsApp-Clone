package com.messenger.whatsappclone.repository;

import com.messenger.whatsappclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Lookup by UUID
    Optional<User> findByUserId(String userId);

    // Lookup by phone number (for login / contact discovery)
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Check uniqueness during registration
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);
}
