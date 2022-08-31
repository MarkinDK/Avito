package com.avito.controllers;

import com.avito.controllers.utils.NewChatDto;
import com.avito.model.User;
import com.avito.services.ChatsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats")
public class ChatsController {
    private final ChatsService chatsService;

    public ChatsController(ChatsService chatsService) {
        this.chatsService = chatsService;
    }

    @PostMapping(value= "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createChat(@RequestBody NewChatDto newChatDto){
        return ResponseEntity.ok(chatsService.addChat(newChatDto));
    }

    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersChats(@RequestBody User user){
        return ResponseEntity.ok(chatsService.getUsersChats(user));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllChats() {
        return ResponseEntity.ok(chatsService.getAllChats());
    }
}
