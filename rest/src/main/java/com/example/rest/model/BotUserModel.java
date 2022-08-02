package com.example.rest.model;

import com.example.rest.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotUserModel {
    public BotUserModel(Long chatId, String userName, String firstName, String lastName) {
        this.chatId = chatId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private Long id;
    private Long chatId;
    private String userName;
    private String firstName;
    private String lastName;
    private String contact;
    private String fullName;
    private Status status;
}