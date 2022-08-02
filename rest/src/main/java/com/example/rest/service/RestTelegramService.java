package com.example.rest.service;
import com.example.rest.common.Currency;
import com.example.rest.constant.Status;
import com.example.rest.model.BotUserModel;
import com.example.rest.model.ClientBalance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface RestTelegramService {
    Double getReceiptCash(Currency currency, LocalDate fromDate, LocalDate toDate);

    Double getReceiptBank(Currency currency, LocalDate fromDate, LocalDate toDate);

    Double getPaymentCash(Currency currency, LocalDate fromDate, LocalDate toDate);

    Double getPaymentBank(Currency currency, LocalDate fromDate, LocalDate toDate);

    Double getTotalReturnedAmountFromClient(Currency currency, LocalDate fromDate, LocalDate toDate);

    ClientBalance getTotalBalanceClient(Currency currency, LocalDate toDate);

    BotUserModel saveBotUserModel(String userName, String firstName, Long chatId, Status status, String contact, String lastName);

}
