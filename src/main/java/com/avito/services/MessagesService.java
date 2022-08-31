package com.avito.services;

import com.avito.controllers.utils.NewMessageDto;
import com.avito.model.Message;
import com.avito.repositories.ChatsRepository;
import com.avito.repositories.MessagesRepository;
import com.avito.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class MessagesService {
    private final MessagesRepository messagesRepository;
    private final ChatsRepository chatsRepository;
    private final UsersRepository usersRepository;

    public MessagesService(MessagesRepository messagesRepository,
                           ChatsRepository chatsRepository,
                           UsersRepository usersRepository) {
        this.messagesRepository = messagesRepository;
        this.chatsRepository = chatsRepository;
        this.usersRepository = usersRepository;
    }

    public String save(NewMessageDto newMessageDto) {
        Message message = new Message();
        message.setChat(chatsRepository.findById(newMessageDto.getChatId())
                .orElseThrow(() -> new NoSuchElementException("Чат с id "
                        + newMessageDto.getChatId()
                        + " не найден")));
        message.setUser(usersRepository.findById(newMessageDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Пользователь с id "
                        + newMessageDto.getUserId()
                        + " не найден")));
        message.initializeId();
        message.initializeCreatedAt();
        message.setText(newMessageDto.getText());
        return messagesRepository.save(message).getId();
    }

    public TreeSet<Message> getAllByChatId(String chatId) {
        chatsRepository.findById(chatId).orElseThrow(() -> new NoSuchElementException("Нет чата с Id = " + chatId));
        return Arrays
                .stream(
                        messagesRepository.getAllByChatId(chatId).orElseThrow(() -> new NoSuchElementException("Нет такого чата")))
                .collect(Collectors.toCollection(TreeSet::new));

    }
}
