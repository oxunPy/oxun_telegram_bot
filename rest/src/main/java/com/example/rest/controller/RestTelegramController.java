package com.example.rest.controller;

import com.example.rest.common.Currency;
import com.example.rest.model.BotUserModel;
import com.example.rest.common.DateRangeAndCurrency;
import com.example.rest.model.ClientBalance;
import com.example.rest.service.impl.RestTelegramServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bot/api")
@RequiredArgsConstructor
public class RestTelegramController{

    private final RestTelegramServiceImpl restTelegramService;

    @PostMapping("/add")
    public BotUserModel saveBotUser(@RequestBody BotUserModel botUser){
        return restTelegramService.saveBotUserModel(
                botUser.getUserName(),
                botUser.getFirstName(),
                botUser.getChatId(),
                botUser.getStatus(),
                botUser.getContact(),
                botUser.getLastName());
    }

    @PostMapping("/get-receipt-cash")
    public Double getReceiptCash(@RequestBody DateRangeAndCurrency dateRangeAndCurrency){
        return restTelegramService.getReceiptCash(dateRangeAndCurrency.getCurrency(), dateRangeAndCurrency.getFromDate(), dateRangeAndCurrency.getToDate());
    }
    @PostMapping("/get-receipt-bank")
    public Double getReceiptBank(@RequestBody DateRangeAndCurrency dateRangeAndCurrency){
        return restTelegramService.getReceiptBank(dateRangeAndCurrency.getCurrency(), dateRangeAndCurrency.getFromDate(), dateRangeAndCurrency.getToDate());
    }
    @PostMapping("/get-payment-cash")
    public Double getPaymentCash(@RequestBody DateRangeAndCurrency dateRangeAndCurrency){
        return restTelegramService.getPaymentCash(dateRangeAndCurrency.getCurrency(), dateRangeAndCurrency.getFromDate(), dateRangeAndCurrency.getToDate());
    }
    @PostMapping("/get-payment-bank")
    public Double getPaymentBank(@RequestBody DateRangeAndCurrency dateRangeAndCurrency){
        return restTelegramService.getPaymentBank(dateRangeAndCurrency.getCurrency(), dateRangeAndCurrency.getFromDate(), dateRangeAndCurrency.getToDate());
    }
    @PostMapping("/get-total-returned-amount-from-client")
    public Double getTotalReturnedAmount(@RequestBody DateRangeAndCurrency dateRangeAndCurrency){
        return restTelegramService.getTotalReturnedAmountFromClient(dateRangeAndCurrency.getCurrency(), dateRangeAndCurrency.getFromDate(), dateRangeAndCurrency.getToDate());
    }
    @PostMapping("/get-total-balance-client")
    public ClientBalance getTotalBalance(@RequestBody DateRangeAndCurrency dateRangeAndCurrency){
        return restTelegramService.getTotalBalanceClient(dateRangeAndCurrency.getCurrency(), dateRangeAndCurrency.getToDate());
    }
}
