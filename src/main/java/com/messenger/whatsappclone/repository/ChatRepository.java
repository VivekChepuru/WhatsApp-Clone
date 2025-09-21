package com.messenger.whatsappclone.repository;

import com.messenger.whatsappclone.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    //Fetch chat by business UUID
    Optional<Chat> findByChatId(String chatId);

    // Fetch multiple chats by their UUIDs (useful for group chats)
    List<Chat> findAllByChatIdIn(List<String> chatIds);

    // Optional: search by name (case-insensitive)
    List<Chat> findByChatNameIgnoreCase(String chatName);
}
