package com.example.telegramterminaluz.menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class UserReplyKeyboardMarkup {

    private String menuName;
    private Boolean isActive;
    private ReplyKeyboardMarkup markup;
    private InlineKeyboardMarkup inlineMarkup;
    private final Bundle bundle;

    public UserReplyKeyboardMarkup(ReplyKeyboardMarkup markup, Bundle bundle) {
        this.markup = markup;
        this.bundle = bundle;
    }

    public UserReplyKeyboardMarkup(InlineKeyboardMarkup inlineMarkup, Bundle bundle) {
        this.inlineMarkup = inlineMarkup;
        this.bundle = bundle;
        state.setState(true);
    }

    public Object getMarkup() {
        return markup != null ? markup : inlineMarkup;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public State getState() {
        return state;
    }

    private final State state = new State() {
        @Override
        public Boolean isActive() {
            return isActive;
        }

        @Override
        public void setState(Boolean state) {
            isActive = state;
        }
    };

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || ! (obj instanceof UserReplyKeyboardMarkup)) return false;
        UserReplyKeyboardMarkup userReplyKeyboardMarkup = (UserReplyKeyboardMarkup) obj;
        return userReplyKeyboardMarkup.getMenuName().equals(getMenuName());
    }
}

interface State {
    Boolean isActive();

    void setState(Boolean state);
}
