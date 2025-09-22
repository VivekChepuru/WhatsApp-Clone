package com.messenger.whatsappclone.repository;

import com.messenger.whatsappclone.entity.Chat;
import com.messenger.whatsappclone.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    // Get all messages in a chat (sorted by timestamp)
    List<Message> findByChatOrderByTimestampAsc(Chat chat);

    // Get last N messages in a chat
    List<Message> findTop50ByChatOrderByTimestampDesc(Chat chat);
}
