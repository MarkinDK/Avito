package com.avito.repositories;

import com.avito.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessagesRepository extends CrudRepository<Message, String> {
    Optional<Message[]> getAllByChatId(String chatId);
}
