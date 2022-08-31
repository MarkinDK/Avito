package com.avito.repositories;

import com.avito.model.Chat;
import com.avito.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatsRepository extends JpaRepository<Chat,String> {
    Optional<Chat> findByName(String chatName);
    Optional<Chat> findById(String id);
//    @Query("")
//    List<Chat> findChatsByUserId(String userId);
    List<Chat> findAllByUsersOrderByChatsMessages(User user);
    List<Chat> findDistinctByUsersOrderByChatsMessagesCreatedAtAsc(User user);

}
