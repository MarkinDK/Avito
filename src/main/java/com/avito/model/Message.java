package com.avito.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message implements Comparable<Message>{
    @Id
    @Column(name = "message_id", nullable = false)
    private String id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference(value = "chatsMessages")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "usersMessages")
    private User user;

    public Message() {
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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", chat=" + chat +
                ", user=" + user +
                '}';
    }

    @Override
    public int compareTo(Message o) {
        return this.createdAt.compareTo(o.createdAt);
    }
}
