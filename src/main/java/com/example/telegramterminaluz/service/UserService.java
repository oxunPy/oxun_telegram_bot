package com.example.telegramterminaluz.service;

import com.example.rest.common.Currency;
import com.example.rest.common.DateRangeAndCurrency;
import com.example.rest.model.BotUserModel;
import com.example.rest.model.ClientBalance;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.time.LocalDate;
@Component
public class UserService {
    @Value("${api.base.url}")
    private String baseUrl;

    public BotUserModel saveUser(Message message){
        return null;
    }

    public Double getReceiptCash(Currency currency, LocalDate fromDate, LocalDate toDate){
        DateRangeAndCurrency dateRangeAndCurrency = new DateRangeAndCurrency(currency, fromDate, toDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject dateRangeObject = new JSONObject();
        dateRangeObject.put("toDate", toDate);
        dateRangeObject.put("fromDate", toDate);
        Double returnedAmount = restTemplate.postForObject(baseUrl + "/get-receipt-cash", dateRangeAndCurrency, Double.class);
        return returnedAmount;
    }

    public Double getReceiptBank(Currency currency, LocalDate fromDate, LocalDate toDate){
        DateRangeAndCurrency dateRangeAndCurrency = new DateRangeAndCurrency(currency, fromDate, toDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject dateRangeObject = new JSONObject();
        dateRangeObject.put("toDate", toDate);
        dateRangeObject.put("fromDate", toDate);
        Double returnedAmount = restTemplate.postForObject(baseUrl + "/get-receipt-bank", dateRangeAndCurrency, Double.class);
        return returnedAmount;
    }

    public Double getPaymentCash(Currency currency, LocalDate fromDate, LocalDate toDate){
        DateRangeAndCurrency dateRangeAndCurrency = new DateRangeAndCurrency(currency, fromDate, toDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject dateRangeObject = new JSONObject();
        dateRangeObject.put("toDate", toDate);
        dateRangeObject.put("fromDate", toDate);
        Double returnedAmount = restTemplate.postForObject(baseUrl + "/get-payment-cash", dateRangeAndCurrency, Double.class);
        return returnedAmount;
    }

    public Double getPaymentBank(Currency currency, LocalDate fromDate, LocalDate toDate){
        DateRangeAndCurrency dateRangeAndCurrency = new DateRangeAndCurrency(currency, fromDate, toDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject dateRangeObject = new JSONObject();
        dateRangeObject.put("toDate", toDate);
        dateRangeObject.put("fromDate", toDate);
        Double returnedAmount = restTemplate.postForObject(baseUrl + "/get-payment-bank", dateRangeAndCurrency, Double.class);
        return returnedAmount;
    }

    public Double getTotalReturnedAmount(Currency currency, LocalDate fromDate, LocalDate toDate){
        DateRangeAndCurrency dateRangeAndCurrency = new DateRangeAndCurrency(currency, fromDate, toDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject dateRangeObject = new JSONObject();
        dateRangeObject.put("toDate", toDate);
        dateRangeObject.put("fromDate", toDate);
        Double returnedAmount = restTemplate.postForObject(baseUrl + "/get-total-returned-amount-from-client", dateRangeAndCurrency, Double.class);
        return returnedAmount;
    }

    public ClientBalance getTotalBalance(Currency currency, LocalDate toDate){
        DateRangeAndCurrency dateRangeAndCurrency = new DateRangeAndCurrency(currency, null, toDate);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject dateRangeObject = new JSONObject();
        dateRangeObject.put("toDate", toDate);
        HttpEntity<String> request = new HttpEntity<String>(dateRangeObject.toString(), headers);
        ClientBalance balance = restTemplate.postForObject(baseUrl + "/get-total-balance-client", dateRangeAndCurrency, ClientBalance.class);
        return balance;
    }
}
