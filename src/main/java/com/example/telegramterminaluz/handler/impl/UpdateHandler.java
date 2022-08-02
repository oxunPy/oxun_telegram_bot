package com.example.telegramterminaluz.handler.impl;

import com.example.telegramterminaluz.handler.Handler;
import com.example.telegramterminaluz.menu.NavigationMenu;
import com.example.telegramterminaluz.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class UpdateHandler implements Handler<Update> {
    private final TelegramService telegramService;
    private final MessageHandler messageHandler;
    private final CallBackQueryHandler callBackQueryHandler;
    private final NavigationMenu navigationMenu;
    @Override
    public void handleMessage(Update update) throws TelegramApiException {
        if (update.hasMessage()) {
            if(messageHandler.getNavigationMenu() == null){
                messageHandler.setNavigationMenu(navigationMenu);
            }
            messageHandler.handleMessage(update.getMessage());
        }
        else if (update.hasCallbackQuery()) {
            if(callBackQueryHandler.getNavigationMenu() == null){
                callBackQueryHandler.setNavigationMenu(navigationMenu);
            }
            callBackQueryHandler.handleMessage(update.getCallbackQuery());
        }
        else{
                   telegramService.executeMessage(SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text(String.format("reply to message %s", update.getMessage()))
                    .replyToMessageId(update.getMessage().getMessageId())
                    .build());
        }
    }
}