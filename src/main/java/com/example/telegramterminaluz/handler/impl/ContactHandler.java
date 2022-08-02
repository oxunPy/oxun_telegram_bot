package com.example.telegramterminaluz.handler.impl;

import com.example.rest.constant.Status;
import com.example.rest.model.BotUserModel;
import com.example.telegramterminaluz.commands.Commands;
import com.example.telegramterminaluz.handler.Handler;
import com.example.telegramterminaluz.service.TelegramService;
import com.example.telegramterminaluz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class ContactHandler implements Handler<Message> {
    private final TelegramService telegramService;
    private final UserService userService;

    @Override
    public void handleMessage(Message message) throws TelegramApiException {
        BotUserModel model = userService.saveUser(message);
        if(model.getStatus() == Status.PASSIVE){
            telegramService.executeMessage(SendMessage
                    .builder()
                    .chatId(message.getChatId().toString())
                    .text("you are registred for being active ask for admin")
                    .replyToMessageId(message.getMessageId())
                    .replyMarkup(Commands.getAdditionalFunctionsMenuKeyBoard())
                    .build());
        }
        else{
            telegramService.executeMessage(SendMessage
                    .builder()
                    .chatId(message.getChatId().toString())
                    .text("you are already registred and activated")
                    .replyToMessageId(message.getMessageId())
                    .replyMarkup(Commands.getAdditionalFunctionsMenuKeyBoard())
                    .build());
        }
    }
}
