package com.example.telegramterminaluz.commands.operation;

import com.example.rest.common.Currency;
import com.example.telegramterminaluz.commands.Commands;
import com.example.telegramterminaluz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class Operations {

    private final UserService userService;
    public String getTodayOperations(Currency currency, String command) {
        if(command.equals(Commands.WHOLE_OPERATION))
            return  "Cегодня: \n" + getWholePeriodOperations(currency, LocalDate.now().minusDays(1), LocalDate.now());
        return "Cегодня: \n" + getPeriodOperation(currency, LocalDate.now().minusDays(1), LocalDate.now(), command);
    }

    public String getThisWeekendOperations(Currency currency, String command) {
        LocalDate now = LocalDate.now();
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(command.equals(Commands.WHOLE_OPERATION))
            return "На этой неделе: \n" + getWholePeriodOperations(currency, now.minusDays(dayOfWeek - DayOfWeek.MONDAY.ordinal()), now);

        return "На этой неделе: \n" + getPeriodOperation(currency, now.minusDays(dayOfWeek - DayOfWeek.MONDAY.ordinal()), now, command);
    }

    public String getThisMonthOperations(Currency currency, String command) {
        LocalDate now = LocalDate.now();
        if(command.equals(Commands.WHOLE_OPERATION))
            return "В этом месяце: \n" + getWholePeriodOperations(currency, LocalDate.of(now.getYear(), now.getMonth(), 1), now);
        return "В этом месяце: \n" + getPeriodOperation(currency, LocalDate.of(now.getYear(), now.getMonth(), 1), now, command);
    }

    public String getPeriodOperation(Currency currency, LocalDate fromDate, LocalDate toDate, String command) {
        switch(command){
            case Commands.GET_PAYMENT_BANK:
                return  capitalize(Commands.GET_PAYMENT_BANK) + ": " + userService.getPaymentBank(currency, fromDate, toDate) +" " + currency;
            case Commands.GET_PAYMENT_CASH:
                return capitalize(Commands.GET_PAYMENT_CASH) + ": " + userService.getPaymentCash(currency, fromDate, toDate) +" " + currency;
            case Commands.GET_RECEIPT_BANK:
                return capitalize(Commands.GET_RECEIPT_BANK) + ": " + userService.getReceiptBank(currency, fromDate, toDate) +" " + currency;
            case Commands.GET_RECEIPT_CASH:
                return capitalize(Commands.GET_RECEIPT_CASH) + ": " + userService.getReceiptCash(currency, fromDate, toDate) +" " + currency;
            case Commands.GET_TOTAL_BALANCE_CLIENT:
                return  capitalize(Commands.GET_TOTAL_BALANCE_CLIENT) + ":\n" + "    кредит = " + userService.getTotalBalance(currency, toDate).getCredit() + " " +currency + "\n"
                                                                  + "    списание средств = " + userService.getTotalBalance(currency, toDate).getDebit() + " " +currency;
            case Commands.GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT:
                return Commands.GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT + ": " + userService.getTotalReturnedAmount(currency, fromDate, toDate) + " " +currency;
        }
        return "";
    }

    public String getWholePeriodOperations(Currency currency, LocalDate fromDate, LocalDate toDate) {
        String result =
                Commands.GET_PAYMENT_CASH + ": " + userService.getPaymentCash(currency, fromDate, toDate) + " " +currency + "\n" +
                Commands.GET_PAYMENT_BANK + ": " + userService.getPaymentBank(currency, fromDate, toDate) + " " +currency + "\n" +
                Commands.GET_RECEIPT_CASH + ": " + userService.getReceiptCash(currency, fromDate, toDate) + " " +currency + "\n" +
                Commands.GET_RECEIPT_BANK + ": " + userService.getReceiptBank(currency, fromDate, toDate) + " " +currency + "\n" +
                Commands.GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT + ": " + userService.getTotalReturnedAmount(currency, fromDate, toDate) + " " +currency + "\n" +
                Commands.GET_TOTAL_BALANCE_CLIENT + ":\n" + "    кредит = " + userService.getTotalBalance(currency, toDate).getCredit() + " " +currency + "\n"
                                    + "    списание средств = " + userService.getTotalBalance(currency, toDate).getDebit() + currency;
        return result;
    }
    private String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}