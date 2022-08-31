package com.avito.controllers.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class NewChatDto {
    @JsonProperty("name")
    private String chatName;
    @JsonProperty("users")
    private String[] users;

    private boolean created = false;
    private String chatId;

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] usersExits) {
        this.users = usersExits;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "NewChatDto{" +
                "chatName = '" + chatName + '\'' +

                ", users = " + Arrays.toString(users) +
                '}';
    }

}
