package com.avito.controllers;

import com.avito.model.User;
import com.avito.services.UsersService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController()
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService service) {
        this.usersService = service;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return ResponseEntity.ok(usersService.saveUser(user));
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usersService.getUser(username).orElseThrow(() ->
                new NoSuchElementException("Пользователь " + username + " не найден")));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }
}
