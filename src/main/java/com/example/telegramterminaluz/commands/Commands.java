package com.example.telegramterminaluz.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    public static final String START = "/start";
    public static final String MENU = "Меню";
    public static final String REMAINING_GOODS = "Остаток товари";
    public static final String AUDIO = "audio";
    public static final String VIDEO = "video";
    public static final String MESSAGE = "message";
    public static final String SYNCHRONIZE = "Синхронизировать";
    public static final String REQUESTADMIN = "request";
    public static final String GET_ALL_SYNC = "Я хочу получить все";

    // VALUTA
    public static final String UZBEK_CURRENCY = "UZS";
    public static final String AMERICAN_CURRENCY = "USD";

    //OPERATIONS
    public static final String GET_PAYMENT_CASH = "Списание наличие дс";
    public static final String GET_PAYMENT_BANK = "Списание без наличных дс";
    public static final String GET_RECEIPT_CASH = "Поступление наличие дс";
    public static final String GET_RECEIPT_BANK = "Поступление без наличных дс";
    public static final String GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT = "Сумма возвратных товоров";
    public static final String GET_TOTAL_BALANCE_CLIENT = "Общий баланс клиента";

    public static final String WHOLE_OPERATION = "Все операция";
    // DATE
    public static final String TODAY_OPERATION = "Сегодняшная операция";
    public static final String THIS_WEEKEND = "Этот неделя";
    public static final String THIS_MONTH = "Этот месяц";
    public static final String OTHER_DATE = "Другие дата";
    public static final String BACK = "Назад";

    public static ReplyKeyboardMarkup getAdditionalFunctionsMenuKeyBoard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);

        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        KeyboardButton paymentCashButton = new KeyboardButton();
        paymentCashButton.setText(GET_PAYMENT_CASH);
        row1.add(paymentCashButton);

        KeyboardButton paymentBankButton = new KeyboardButton();
        paymentBankButton.setText(GET_PAYMENT_BANK);
        row1.add(paymentBankButton);

        KeyboardButton receiptCashButton = new KeyboardButton();
        receiptCashButton.setText(GET_RECEIPT_CASH);
        row1.add(receiptCashButton);

        KeyboardButton receiptBankButton = new KeyboardButton();
        receiptBankButton.setText(GET_RECEIPT_BANK);
        row2.add(receiptBankButton);

        KeyboardButton returnedAmountButton = new KeyboardButton();
        returnedAmountButton.setText(GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT);
        row2.add(returnedAmountButton);

        KeyboardButton totalBalanceButton = new KeyboardButton();
        totalBalanceButton.setText(GET_TOTAL_BALANCE_CLIENT);
        row2.add(totalBalanceButton);

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        markup.setKeyboard(rows);
        return markup;
    }

    public static ReplyKeyboardMarkup getShareContactKeyBoard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        markup.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardButton buttonAskContact = new KeyboardButton();
        buttonAskContact.setRequestContact(true);
        buttonAskContact.setText("Share contact");
        row.add(buttonAskContact);
        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }

    public static ReplyKeyboardMarkup getWholeOperationKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();

        KeyboardButton wholeOperationButton = new KeyboardButton();
        wholeOperationButton.setText(WHOLE_OPERATION);

        ReplyKeyboardMarkup additionalFunctionsMarkup = getAdditionalFunctionsMenuKeyBoard();

        firstRow.add(wholeOperationButton);
        rows.add(firstRow);
        rows.addAll(additionalFunctionsMarkup.getKeyboard());
        markup.setKeyboard(rows);
        return markup;
    }

    public static ReplyKeyboardMarkup getCurrencyKeyboardMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        KeyboardButton uzsButton = new KeyboardButton();
        uzsButton.setText(UZBEK_CURRENCY);

        KeyboardButton usdButton = new KeyboardButton();
        usdButton.setText(AMERICAN_CURRENCY);

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(BACK);

        row1.add(uzsButton);
        row1.add(usdButton);
        row2.add(backButton);
        rows.add(row1);
        rows.add(row2);

        markup.setKeyboard(rows);
        return markup;
    }

    public static ReplyKeyboardMarkup getDateMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setOneTimeKeyboard(true);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        KeyboardButton todayOperationButton = new KeyboardButton();
        todayOperationButton.setText(TODAY_OPERATION);

        KeyboardButton thisWeekendButton = new KeyboardButton();
        thisWeekendButton.setText(THIS_WEEKEND);

        KeyboardButton thisMonthButton = new KeyboardButton();
        thisMonthButton.setText(THIS_MONTH);

        KeyboardButton otherDate = new KeyboardButton();
        otherDate.setText(OTHER_DATE);

        KeyboardButton backButton = new KeyboardButton();
        backButton.setText(BACK);

        row1.add(todayOperationButton);
        row1.add(thisWeekendButton);
        row2.add(thisMonthButton);
        row2.add(otherDate);
        row3.add(backButton);

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        markup.setKeyboard(rows);
        return markup;
    }
    public ReplyKeyboardMarkup getConnectionDbMarkup(){
        ReplyKeyboardMarkup connectionDBMarkup = new ReplyKeyboardMarkup();
        connectionDBMarkup.setResizeKeyboard(true);
        connectionDBMarkup.setSelective(true);
        connectionDBMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton dbUrlButton = new KeyboardButton();
        dbUrlButton.setText("send db url");

        KeyboardButton dbPasswordButton = new KeyboardButton();
        dbPasswordButton.setText("send db password");

        KeyboardButton dbUserNameButton = new KeyboardButton();
        dbUserNameButton.setText("send db username");
        row.add(dbUrlButton);
        rows.add(row);

        // reset row
        row = new KeyboardRow();
        row.add(dbPasswordButton);
        row.add(dbUserNameButton);
        rows.add(row);

        connectionDBMarkup.setKeyboard(rows);
        return connectionDBMarkup;
    }
}
