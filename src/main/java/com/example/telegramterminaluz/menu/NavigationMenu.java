package com.example.telegramterminaluz.menu;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Iterator;
import java.util.Stack;

@Service
@Getter
public class NavigationMenu {
    private Stack<UserReplyKeyboardMarkup> menus = new Stack<>();

    public void startMenu(UserReplyKeyboardMarkup newMenu){
        if(!menus.isEmpty()){
            UserReplyKeyboardMarkup oldMenu = menus.peek();
            if(oldMenu.getState().isActive()) oldMenu.getState().setState(false);
        }
        newMenu.getState().setState(true);
        menus.push(newMenu);
    }

    public void addMenuButton(KeyboardButton button, int rowIndex){
        UserReplyKeyboardMarkup topMenu = menus.peek();
        if(rowIndex > ((ReplyKeyboardMarkup)topMenu.getMarkup()).getKeyboard().size()){
            KeyboardRow newRow = new KeyboardRow();
            newRow.add(button);
            ((ReplyKeyboardMarkup)topMenu.getMarkup()).getKeyboard().add(newRow);
        }
        else{
            ((ReplyKeyboardMarkup)topMenu.getMarkup()).getKeyboard().get(rowIndex).add(button);
        }
    }

    public UserReplyKeyboardMarkup peekMenu(){
        UserReplyKeyboardMarkup lastMenu = menus.peek();
        return lastMenu;
    }

    public boolean hasMenu(UserReplyKeyboardMarkup userReplyKeyboardMarkup){
        if(menus.isEmpty() || userReplyKeyboardMarkup.getMenuName() == null || userReplyKeyboardMarkup.getMenuName().isEmpty()) return false;

        Iterator<UserReplyKeyboardMarkup> iter = menus.iterator();
        while(iter.hasNext()){
            if(iter.next().equals(userReplyKeyboardMarkup)) return true;
        }
        return false;
    }

    public boolean hasMenu(String menuName){
        if(menus.isEmpty() || menuName  == null || menuName.isEmpty()) return false;

        Iterator<UserReplyKeyboardMarkup> iter = menus.iterator();
        while(iter.hasNext()){
            if(iter.next().getMenuName().equals(menuName)) return true;
        }
        return false;
    }

    public void deleteFollowingMenusAfter(String menuName){
        while(!menus.isEmpty() && !menus.peek().getMenuName().equals(menuName)){
            menus.pop();
        }
    }




}
