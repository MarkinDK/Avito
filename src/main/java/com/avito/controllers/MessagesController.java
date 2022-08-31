package com.avito.controllers;

import com.avito.controllers.utils.NewMessageDto;
import com.avito.services.ChatsService;
import com.avito.services.MessagesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessagesController {
    private final MessagesService messagesService;
    private final ChatsService chatsService;

    public MessagesController(MessagesService messagesService, ChatsService chatsService) {
        this.messagesService = messagesService;
        this.chatsService = chatsService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMessage(@RequestBody NewMessageDto message){
        return ResponseEntity.ok(messagesService.save(message));
    }

    @PostMapping("/get")
    public ResponseEntity<?> getMessages(@RequestBody String chatInfo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(chatInfo);
        String chatId = jsonNode.get("chat").asText();
        return ResponseEntity.ok(messagesService.getAllByChatId(chatId));
    }
}
