package com.example.telegramterminaluz.handler.impl;

import com.example.telegramterminaluz.commands.GeneralCommandHandler;
import com.example.telegramterminaluz.handler.Handler;
import com.example.telegramterminaluz.menu.NavigationMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class MessageHandler implements Handler<Message> {

    private final GeneralCommandHandler generalCommandHandler;
    private final ContactHandler contactHandler;
    private NavigationMenu navigationMenu;

    public void handleMessage(Message message) throws TelegramApiException {
        if(message.hasText()){
            generalCommandHandler.setNavigationMenu(navigationMenu);
            generalCommandHandler.executeCommand(message, message.getText());
        }
        if(message.hasContact()){
            contactHandler.handleMessage(message);
        }
    }

    public NavigationMenu getNavigationMenu() {
        return navigationMenu;
    }

    public void setNavigationMenu(NavigationMenu navigationMenu) {
        this.navigationMenu = navigationMenu;
    }

}
