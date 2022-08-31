package com.avito.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private String id;

    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_chats",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    )
    @JsonIgnore
    private Set<Chat> usersChats = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Message> usersMessages = new HashSet<>();

    public User() {
    }

    public String initializeId() {
        id = UUID.randomUUID().toString();
        return id;
    }

    public LocalDateTime initializeCreatedAt() {
        createdAt = LocalDateTime.now();
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Chat> getUsersChats() {
        return usersChats;
    }

    public void setUsersChats(Set<Chat> usersChats) {
        this.usersChats = usersChats;
    }

    public Set<Message> getUsersMessages() {
        return usersMessages;
    }

    public void setUsersMessages(Set<Message> messages) {
        this.usersMessages = messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "id = '" + id + '\'' +
                ", username = '" + username + '\'' +
                ", createdAt = " + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                createdAt.equals(user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, createdAt);
    }

    public void addChat(Chat chat) {
        usersChats.add(chat);
    }

    public boolean addMessage(Message message) {
        return usersMessages.add(message);
    }
}
