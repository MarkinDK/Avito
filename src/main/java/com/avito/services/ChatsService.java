package com.avito.services;

import com.avito.controllers.utils.NewChatDto;
import com.avito.exceptions.ChatCreatingException;
import com.avito.exceptions.ElementAlreadyExists;
import com.avito.model.Chat;
import com.avito.model.User;
import com.avito.repositories.ChatsRepository;
import com.avito.repositories.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class ChatsService {
    private final ChatsRepository chatsRepository;
    private final UsersRepository usersRepository;

    public ChatsService(ChatsRepository chatsRepository, UsersRepository usersRepository) {
        this.chatsRepository = chatsRepository;
        this.usersRepository = usersRepository;
    }

    public NewChatDto addChat(NewChatDto newChatDto) {
        Set<User> usersExist = checkUsersIfExist(newChatDto);

        NewChatDto result = new NewChatDto();
        result.setChatName(newChatDto.getChatName());
        if (usersExist.size() > 1) {
            Chat chat = new Chat();
            chat.initializeCreatedAt();
            chat.initializeId();
            chat.setName(newChatDto.getChatName());
            usersExist.forEach(user -> user.addChat(chat));
            chat.setUsers(usersExist);
            try {
                chatsRepository.save(chat);
            } catch (DataIntegrityViolationException exception) {
                if (exception
                        .getMostSpecificCause()
                        .getClass()
                        .getName()
                        .equals("org.postgresql.util.PSQLException") &&
                        ((SQLException) exception
                                .getMostSpecificCause())
                                .getSQLState()
                                .equals("23505")) {
                    throw new ElementAlreadyExists("Чат " + chat.getName() + " уже существует.");
                }
                throw exception;
            }
            result.setCreated(true);
            result.setChatId(chat.getId());
            result.setUsers(usersExist.stream().map(User::getId).toArray(String[]::new));
        }
        return result;
    }

    private HashSet<User> checkUsersIfExist(NewChatDto newChatDto) {
        int numberOfUsers = newChatDto.getUsers().length;
        if (numberOfUsers == 2 && (newChatDto.getUsers()[0].equals(newChatDto.getUsers()[1])))
            throw new ChatCreatingException("Нельзя создать чат для пользователя с самим собой");
        if (numberOfUsers < 2)
            throw new ChatCreatingException("Нельзя создать чат для " + numberOfUsers + " пользователей");
        if (new HashSet<>(Arrays.asList(newChatDto.getUsers())).size() < 2)
            throw new ChatCreatingException("Нельзя создать чат с одним пользователем");
        HashSet<User> users = new HashSet<>();
        Arrays.stream(newChatDto.getUsers()).forEach((id) -> usersRepository.findById(id).ifPresent(users::add));
        return users;
    }

    public Optional<Chat> getChat(String chatName) {
        return chatsRepository.findByName(chatName);
    }

    public Iterable<Chat> getAllChats() {
        return chatsRepository.findAll();
    }

    public TreeSet<Chat> getUsersChats(User user) {
        User result = usersRepository.findById(user.getId()).orElseThrow(() -> new NoSuchElementException("No such user"));
        return new TreeSet<>(result.getUsersChats());
    }
}
