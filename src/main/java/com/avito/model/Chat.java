package com.avito.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "chats")
public class Chat implements Comparable<Chat> {
    @Id
    @Column(name = "chat_id", nullable = false)
    private String id;
    @Column(name = "chat_name", nullable = false)
    private String name;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "usersChats", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private Set<Message> chatsMessages = new HashSet<>();

    public Chat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Message> getChatsMessages() {
        return chatsMessages;
    }

    public void setChatsMessages(Set<Message> messages) {
        this.chatsMessages = messages;
    }

    public LocalDateTime initializeCreatedAt() {
        createdAt = LocalDateTime.now();
        return createdAt;
    }

    public String initializeId() {
        id = UUID.randomUUID().toString();
        return id;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                ", users = " + users +
                ", createdAt = " + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id.equals(chat.id) &&
                name.equals(chat.name) &&
                Objects.equals(users, chat.users) &&
                createdAt.equals(chat.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt);
    }

    public boolean addMessage(Message message) {
        return chatsMessages.add(message);
    }

    @Override
    public int compareTo(Chat o) {
        if (chatsMessages.size() > 0 && o.chatsMessages.size() > 0) {
            TreeSet<Message> messagesThis = new TreeSet<>(chatsMessages);
            TreeSet<Message> messagesThat = new TreeSet<>(o.chatsMessages);
            return -messagesThis.last().getCreatedAt().compareTo(messagesThat.last().getCreatedAt());
        }
        if (chatsMessages.size() > 0 && o.chatsMessages.size() == 0) {
            TreeSet<Message> messagesThis = new TreeSet<>(chatsMessages);
            return -messagesThis.last().getCreatedAt().compareTo(o.createdAt);
        }
        if (chatsMessages.size() == 0 && o.chatsMessages.size() > 0) {
            TreeSet<Message> messagesThat = new TreeSet<>(o.chatsMessages);
            return -this.createdAt.compareTo(messagesThat.last().getCreatedAt());
        }

        return -createdAt.compareTo(o.createdAt);
    }
}
//   a.compareTo(b) = 1 -> a>b
//   a.compareTo(b) = 0 -> a=b
//   a.compareTo(b) =-1 -> a<b