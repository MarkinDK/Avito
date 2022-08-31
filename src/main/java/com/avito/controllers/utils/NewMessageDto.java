package com.avito.controllers.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewMessageDto {
    @JsonProperty("chat")
    private String chatId;
    @JsonProperty("author")
    private String userId;
    @JsonProperty("text")
    private String text;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NewMessageDto{" +
                "chatId='" + chatId + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
