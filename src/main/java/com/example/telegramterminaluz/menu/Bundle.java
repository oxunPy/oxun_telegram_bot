package com.example.telegramterminaluz.menu;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Bundle {
    public static final String CURRENCY = "currency";
    public static final String FROM_DATE = "from_date";
    public static final String TO_DATE = "to_date";
    public static final String COMMAND = "command";

    private Map<String, Object> cache = new HashMap<>();

    public Bundle put(String key, Object obj) {
        cache.put(key, obj);
        return this;
    }

    public Object get(String key) {
        if (cache.containsKey(key))
            return cache.get(key);
        return null;
    }

    public Integer getInt(String key, Integer defaultValue) {
        if (cache.containsKey(key) && cache.get(key) instanceof Integer) {
            return (Integer) cache.get(key);
        } else
            return defaultValue;
    }

    public Integer getInt(String key) {
        return getInt(key, 0);
    }

    public boolean has(String key) {
        return cache.containsKey(key);
    }

    public Bundle copy(Bundle bundle) {
        cache.putAll(bundle.cache);
        return this;
    }

    public static Bundle create() {
        return new Bundle();
    }
}
