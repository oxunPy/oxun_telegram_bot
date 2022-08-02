package com.example.telegramterminaluz.handler;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Handler<T> {

    void handleMessage(T t) throws TelegramApiException;
}
