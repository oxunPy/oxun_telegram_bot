package com.example.telegramterminaluz.commands;

import com.example.rest.TestConnection;
import com.example.rest.common.Currency;
import com.example.telegramterminaluz.calendar.CalendarMarkup;
import com.example.telegramterminaluz.commands.operation.Operations;
import com.example.telegramterminaluz.menu.Bundle;
import com.example.telegramterminaluz.menu.NavigationMenu;
import com.example.telegramterminaluz.menu.UserReplyKeyboardMarkup;
import com.example.telegramterminaluz.service.TelegramService;
import com.example.telegramterminaluz.service.UserService;
import com.example.telegramterminaluz.utils.CalendarMarkupUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class GeneralCommandHandler implements CommandHandler<Message> {

    private final TelegramService telegramService;
    private final UserService userService;
    private final Operations operations;
    private NavigationMenu navigationMenu;

    @Override
    public void executeCommand(Message message, String command) throws TelegramApiException {
        Currency currency = !navigationMenu.getMenus().isEmpty() ? (Currency) navigationMenu.getMenus().peek().getBundle().get(Bundle.CURRENCY) : null;
        String commandMethod = !navigationMenu.getMenus().isEmpty() ? (String) navigationMenu.getMenus().peek().getBundle().get(Bundle.COMMAND) : null;

        if (message.getText().startsWith("url:") && navigationMenu.hasMenu("login")) {
            Bundle loginBundle = navigationMenu.peekMenu().getBundle();
            loginBundle.put(message.getText().split(":")[0].trim(), message.getText().split(":")[1].trim());

            telegramService.executeMessage(
                    SendMessage.builder()
                            .text("Отправить имя пользователя базы данных")
                            .chatId(message.getChatId().toString())
                            .build());

        } else if (message.getText().startsWith("user:") && navigationMenu.hasMenu("login")) {
            Bundle loginBundle = navigationMenu.peekMenu().getBundle();
            loginBundle.put(message.getText().split(":")[0].trim(), message.getText().split(":")[1].trim());

            telegramService.executeMessage(
                    SendMessage.builder()
                            .text("Отправить пароль базы данных")
                            .chatId(message.getChatId().toString())
                            .build());
        } else if (message.getText().startsWith("password:") && navigationMenu.hasMenu("login")) {
            Bundle loginBundle = navigationMenu.peekMenu().getBundle();
            loginBundle.put(message.getText().split(":")[0].trim(), message.getText().split(":")[1].trim());

            // check database connection if connection is success send whole-operations-menu, else send retype connection properties again
            if(TestConnection.connection1(loginBundle.get("url").toString(), loginBundle.get("user").toString(), loginBundle.get("password").toString())){

                ReplyKeyboardMarkup wholeOperationKeyboard = Commands.getWholeOperationKeyboard();
                UserReplyKeyboardMarkup userWholeMarkupKeyboard = new UserReplyKeyboardMarkup(wholeOperationKeyboard, new Bundle());
                userWholeMarkupKeyboard.setMenuName(Commands.START);

                if (navigationMenu.hasMenu(userWholeMarkupKeyboard))
                    navigationMenu.deleteFollowingMenusAfter(userWholeMarkupKeyboard.getMenuName());
                else
                    navigationMenu.startMenu(userWholeMarkupKeyboard);

                                telegramService.executeMessage(
                        SendMessage.builder()
                                .text("привет " + message.getFrom().getFirstName())
                                .chatId(message.getChatId().toString())
                                .replyMarkup(wholeOperationKeyboard)
                                .build()
                );

            }
            else{
                telegramService.executeMessage(
                        SendMessage.builder()
                                .text("Что-то пошло не так, пришлите мне ссылку на базу данных еще раз...")
                                .chatId(message.getChatId().toString())
                                .build());
            }

        } else {

            switch (command) {
                case Commands.START:


                    // login db user inputs
                    Bundle loginBundle = new Bundle();
                    UserReplyKeyboardMarkup userLoginDB = new UserReplyKeyboardMarkup(new ReplyKeyboardMarkup(), loginBundle);
                    userLoginDB.setMenuName("login");
                    if(navigationMenu.hasMenu(userLoginDB)){
                        navigationMenu.deleteFollowingMenusAfter("login");
                    }
                    else{
                        navigationMenu.startMenu(userLoginDB);
                    }


                    telegramService.executeMessage(
                            SendMessage.builder()
                                    .text("Отправить информацию о базе данных " +
                                            "Например:\n" +
                                            "url: my_database_name\n" +
                                            "user: my_login_name\n" +
                                            "password: my_login_password")
                                    .chatId(message.getChatId().toString())
                                    .build());

                    telegramService.executeMessage(
                            SendMessage.builder()
                                    .text("Отправьте мне адрес базы данных")
                                    .chatId(message.getChatId().toString())
                                    .build());

                    break;

                case Commands.WHOLE_OPERATION:
                    ReplyKeyboardMarkup currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle wholeOperationsBundle = new Bundle();
                    wholeOperationsBundle.put(Bundle.COMMAND, Commands.WHOLE_OPERATION);
                    UserReplyKeyboardMarkup userCurrencyMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, wholeOperationsBundle);
                    userCurrencyMarkup.setMenuName(Commands.WHOLE_OPERATION);

                    if (navigationMenu.hasMenu(userCurrencyMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userCurrencyMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userCurrencyMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("whole operations")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;

                case Commands.UZBEK_CURRENCY:
                    ReplyKeyboardMarkup menuMarkupUZS = Commands.getDateMarkup();
                    Bundle menuUzBundle = new Bundle();
                    menuUzBundle.put(Bundle.CURRENCY, Currency.UZS);
                    UserReplyKeyboardMarkup userMenuMarkupUZS = new UserReplyKeyboardMarkup(menuMarkupUZS, menuUzBundle.copy(navigationMenu.getMenus().peek().getBundle()));
                    userMenuMarkupUZS.setMenuName(Commands.UZBEK_CURRENCY);

                    if (navigationMenu.hasMenu(userMenuMarkupUZS))
                        navigationMenu.deleteFollowingMenusAfter(userMenuMarkupUZS.getMenuName());
                    else
                        navigationMenu.startMenu(userMenuMarkupUZS);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("uzs")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(menuMarkupUZS)
                            .build());
                    break;

                case Commands.AMERICAN_CURRENCY:
                    ReplyKeyboardMarkup menuMarkupUSD = Commands.getDateMarkup();
                    Bundle menuUSBundle = new Bundle();
                    menuUSBundle.put(Bundle.CURRENCY, Currency.USD);
                    UserReplyKeyboardMarkup userMenuMarkupUSD = new UserReplyKeyboardMarkup(menuMarkupUSD, menuUSBundle.copy(navigationMenu.getMenus().peek().getBundle()));
                    userMenuMarkupUSD.setMenuName(Commands.AMERICAN_CURRENCY);

                    if (navigationMenu.hasMenu(userMenuMarkupUSD))
                        navigationMenu.deleteFollowingMenusAfter(userMenuMarkupUSD.getMenuName());
                    else
                        navigationMenu.startMenu(userMenuMarkupUSD);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("usd")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(menuMarkupUSD)
                            .build());
                    break;

                case Commands.TODAY_OPERATION:
                    telegramService.executeMessage(SendMessage.builder()
                            .text(operations.getTodayOperations(currency, commandMethod))
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(Commands.getDateMarkup())
                            .build());
                    break;

                case Commands.THIS_WEEKEND:
                    telegramService.executeMessage(SendMessage.builder()
                            .text(operations.getThisWeekendOperations(currency, commandMethod))
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(Commands.getDateMarkup())
                            .build());
                    break;

                case Commands.THIS_MONTH:
                    telegramService.executeMessage(SendMessage.builder()
                            .text(operations.getThisMonthOperations(currency, commandMethod))
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(Commands.getDateMarkup())
                            .build());
                    break;

                case Commands.OTHER_DATE:

                    InlineKeyboardMarkup dateToDateCalendar = new CalendarMarkupUtils().getFromDateToDateCalendar();
                    InlineKeyboardMarkup toDateCalendar = new CalendarMarkupUtils().convertToToDateCalendarMarkup(new CalendarMarkup().getCalendarInstance());
                    Bundle otherDateBundle = new Bundle().copy(navigationMenu.getMenus().peek().getBundle());
                    UserReplyKeyboardMarkup userOtherDateMarkup = new UserReplyKeyboardMarkup(dateToDateCalendar, otherDateBundle);
                    userOtherDateMarkup.setMenuName(Commands.OTHER_DATE);

                    if (navigationMenu.hasMenu(userOtherDateMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userOtherDateMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userOtherDateMarkup);


                    telegramService.executeMessage(SendMessage.builder()
                            .text("other date")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(commandMethod != null && commandMethod.equals(Commands.GET_TOTAL_BALANCE_CLIENT) ? toDateCalendar : dateToDateCalendar)
                            .build());
                    break;

                case Commands.GET_PAYMENT_CASH:
                    currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle paymentCashBundle = new Bundle();
                    paymentCashBundle.put(Bundle.COMMAND, Commands.GET_PAYMENT_CASH);
                    UserReplyKeyboardMarkup userPaymentCashMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, paymentCashBundle);
                    userPaymentCashMarkup.setMenuName(Commands.GET_PAYMENT_CASH);

                    if (navigationMenu.hasMenu(userPaymentCashMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userPaymentCashMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userPaymentCashMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("get payment cash")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;

                case Commands.GET_PAYMENT_BANK:
                    currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle paymentBankBundle = new Bundle();
                    paymentBankBundle.put(Bundle.COMMAND, Commands.GET_PAYMENT_BANK);
                    UserReplyKeyboardMarkup userPaymentBankMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, paymentBankBundle);
                    userPaymentBankMarkup.setMenuName(Commands.GET_PAYMENT_BANK);

                    if (navigationMenu.hasMenu(userPaymentBankMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userPaymentBankMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userPaymentBankMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("get payment bank")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;

                case Commands.GET_RECEIPT_BANK:
                    currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle receiptBankBundle = new Bundle();
                    receiptBankBundle.put(Bundle.COMMAND, Commands.GET_RECEIPT_BANK);
                    UserReplyKeyboardMarkup userReceiptBankMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, receiptBankBundle);
                    userReceiptBankMarkup.setMenuName(Commands.GET_RECEIPT_BANK);

                    if (navigationMenu.hasMenu(userReceiptBankMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userReceiptBankMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userReceiptBankMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("get receipt bank")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;

                case Commands.GET_RECEIPT_CASH:
                    currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle receiptCashBundle = new Bundle();
                    receiptCashBundle.put(Bundle.COMMAND, Commands.GET_RECEIPT_CASH);
                    UserReplyKeyboardMarkup userReceiptCashMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, receiptCashBundle);
                    userReceiptCashMarkup.setMenuName(Commands.GET_RECEIPT_CASH);

                    if (navigationMenu.hasMenu(userReceiptCashMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userReceiptCashMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userReceiptCashMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("get reciept cash")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;

                case Commands.GET_TOTAL_BALANCE_CLIENT:
                    currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle totalBalanceBundle = new Bundle();
                    totalBalanceBundle.put(Bundle.COMMAND, Commands.GET_TOTAL_BALANCE_CLIENT);
                    UserReplyKeyboardMarkup userTotalBalanceMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, totalBalanceBundle);
                    userTotalBalanceMarkup.setMenuName(Commands.GET_TOTAL_BALANCE_CLIENT);

                    if (navigationMenu.hasMenu(userTotalBalanceMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userTotalBalanceMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userTotalBalanceMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("get total balance client")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;

                case Commands.GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT:
                    currencyKeyboardMarkup = Commands.getCurrencyKeyboardMarkup();
                    Bundle totalReturnedBundle = new Bundle();
                    totalReturnedBundle.put(Bundle.COMMAND, Commands.GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT);
                    UserReplyKeyboardMarkup userTotalReturnedMarkup = new UserReplyKeyboardMarkup(currencyKeyboardMarkup, totalReturnedBundle);
                    userTotalReturnedMarkup.setMenuName(Commands.GET_TOTAL_RETURNED_AMOUNT_FROM_CLIENT);

                    if (navigationMenu.hasMenu(userTotalReturnedMarkup))
                        navigationMenu.deleteFollowingMenusAfter(userTotalReturnedMarkup.getMenuName());
                    else
                        navigationMenu.startMenu(userTotalReturnedMarkup);

                    telegramService.executeMessage(SendMessage.builder()
                            .text("get total returned amount from client")
                            .chatId(String.valueOf(message.getChatId()))
                            .replyMarkup(currencyKeyboardMarkup)
                            .build());
                    break;


                case Commands.BACK:
                    if (navigationMenu.getMenus().peek().getMenuName().equals(Commands.OTHER_DATE)) {
                        navigationMenu.getMenus().pop();
                    }
                    navigationMenu.getMenus().pop();
                    telegramService.executeMessage(SendMessage.builder()
                            .text("back")
                            .chatId(message.getChatId().toString())
                            .replyMarkup((ReplyKeyboardMarkup) navigationMenu.peekMenu().getMarkup())
                            .build());
            }
        }
    }

    public void setNavigationMenu(NavigationMenu navigationMenu) {
        this.navigationMenu = navigationMenu;
    }
}
