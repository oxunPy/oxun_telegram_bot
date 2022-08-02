package com.example.rest.service.impl;

import com.example.rest.common.Currency;
import com.example.rest.constant.Status;
import com.example.rest.data_interface.BalanceInterface;
import com.example.rest.model.BotUserModel;
import com.example.rest.model.ClientBalance;
import com.example.rest.repository.RestTelegramRepository;
import com.example.rest.service.RestTelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestTelegramServiceImpl implements RestTelegramService {

    @Autowired
    private final RestTelegramRepository restTemplateRepository;

    @Transactional(readOnly = true)
    public Double getReceiptCash(Currency currency, LocalDate fromDate, LocalDate toDate) {
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }

        if (fromDate != null && toDate != null) {
            Optional<Double> result = restTemplateRepository.getReceiptCash(currency.ordinal() + 1, fromDate, toDate);
            if (result.isPresent()) return withBigDecimal(result.get(), 3);
        }
        return 0.00;
    }

    @Transactional(readOnly = true)
    public Double getReceiptBank(Currency currency, LocalDate fromDate, LocalDate toDate) {
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }
        if (fromDate != null && toDate != null) {
            Optional<Double> result = restTemplateRepository.getReceiptBank(currency.ordinal() + 1, fromDate, toDate);
            if (result.isPresent()) return withBigDecimal(result.get(), 3);
        }
        return 0.00;
    }

    @Transactional(readOnly = true)
    public Double getPaymentCash(Currency currency, LocalDate fromDate, LocalDate toDate) {
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }
        if (fromDate != null && toDate != null) {
            Optional<Double> result = restTemplateRepository.getPaymentCash(currency.ordinal() + 1, fromDate, toDate);
            if (result.isPresent()) return withBigDecimal(result.get(), 3);
        }
        return 0.00;
    }

    @Transactional(readOnly = true)
    public Double getPaymentBank(Currency currency, LocalDate fromDate, LocalDate toDate) {
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }
        if (fromDate != null && toDate != null) {
            Optional<Double> result = restTemplateRepository.getPaymentBank(currency.ordinal() + 1, fromDate, toDate);
            if (result.isPresent()) return withBigDecimal(result.get(),   3);
        }
        return 0.00;
    }

    @Transactional(readOnly = true)
    public Double getTotalReturnedAmountFromClient(Currency currency, LocalDate fromDate, LocalDate toDate) {
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }
        if (fromDate != null && toDate != null) {
            Optional<Double> result = restTemplateRepository.getTotalReturnedAmountFromClient(currency.ordinal() + 1, fromDate, toDate);
            if (result.isPresent()) return withBigDecimal(result.get(), 3);
        }
        return 0.00;
    }

    @Transactional(readOnly = true)
    public ClientBalance getTotalBalanceClient(Currency currency, LocalDate toDate) {
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }
        ClientBalance clientBalance = new ClientBalance(BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00));
        if (toDate != null) {
            Optional<BalanceInterface> result = restTemplateRepository.getTotalBalanceClient(currency.ordinal() + 1, toDate);
            if (result.isPresent()) {
                clientBalance.setCredit(result.get().getCredit() != null ? formatBigDecimal(result.get().getCredit(), 3)
                                                                         : formatBigDecimal(BigDecimal.valueOf(0.00), 3));
                clientBalance.setDebit(result.get().getDebit() != null   ?  formatBigDecimal(result.get().getDebit(), 3)
                                                                         : formatBigDecimal(BigDecimal.valueOf(0.00), 3));
            }
        }
        return clientBalance;
    }

    @Transactional
    public BotUserModel saveBotUserModel(String userName, String firstName, Long chatId, Status status, String contact, String lastName) {
        restTemplateRepository.save(userName, firstName, chatId, status, contact, lastName);
        BotUserModel model = new BotUserModel();
        model.setUserName(userName);
        model.setFirstName(firstName);
        model.setChatId(chatId);
        model.setStatus(status);
        model.setContact(contact);
        model.setLastName(lastName);
        return model;
    }

    private double withBigDecimal(double value, int places) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    private BigDecimal formatBigDecimal(BigDecimal value, int places){
        value = value.setScale(places, RoundingMode.HALF_UP);
        return value;
    }


}
