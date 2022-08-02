package com.example.telegramterminaluz.utils;

import com.example.rest.common.Currency;
import com.example.telegramterminaluz.calendar.CalendarMarkup;

import java.time.LocalDate;

public class StringUtils {

    public static Currency getCurrency(String text) {           // pairs[0] - currency, pairs[1] - method, pairs[2] - from_date, pairs[3] - to_date
        String[] pairs = text.split(",");
        if(pairs.length > 0 && pairs[0].contains("currency")){
            String currencyVal = pairs[0].split(":")[1];        // currency : uzs
            if(currencyVal.equals("uzs")){
                return Currency.UZS;
            }
            return Currency.USD;
        }
        return null;
    }

    public static String getMethod(String text) {
        String[] pairs = text.split(",");
        Integer methodIndex = getIndexByValue(pairs, "method");
        if(methodIndex != -1){
            String methodVal = pairs[methodIndex].split(":")[1];          // method : Commands.GET_PAYMENT_CASH
            return methodVal;
        }
        return null;
    }

    public static LocalDate getFromDate(String text) {
        String[] pairs = text.split(",");
        Integer fromDateIndex = getIndexByValue(pairs, "from_date");
        if(fromDateIndex != -1){
            String fromDate = pairs[fromDateIndex].split(":")[1];          // method : Commands.GET_PAYMENT_CASH
            return LocalDate.of(Integer.parseInt(fromDate.split("-")[0]), Integer.parseInt(fromDate.split("-")[1]), Integer.parseInt(fromDate.split("-")[2]));
        }
        return null;
    }
    public static LocalDate getToDate(String text) {
        String[] pairs = text.split(",");
        Integer toDateIndex = getIndexByValue(pairs, "to_date");
        if(toDateIndex != -1){
            String toDate = pairs[toDateIndex].split(":")[1];          // method : Commands.GET_PAYMENT_CASH
            return LocalDate.of(Integer.parseInt(toDate.split("-")[0]), Integer.parseInt(toDate.split("-")[1]), Integer.parseInt(toDate.split("-")[2]));
        }
        return null;
    }

    private static Integer getIndexByValue(String[] array, String value){
        for(int i = 0; i < array.length; i++){
            if(array[i].contains(value)) return i;
        }
        return -1;
    }
}
