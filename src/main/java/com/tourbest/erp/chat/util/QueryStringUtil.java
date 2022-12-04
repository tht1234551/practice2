package com.tourbest.erp.chat.util;


import java.util.HashMap;
import java.util.Map;

public class QueryStringUtil {

    public static Map<String, String> toMap(String queryString) {
        Map<String, String> map = new HashMap<>();

        String[] items = queryString.split("&");

        for (String item : items) {
            addItem(item, map);
        }

        return map;
    }

    private static void addItem(String item, Map<String, String> map) {
        String[] temp = item.split("=");

        try {
            String key = temp[0];
            String value = temp[1];

            map.put(key, value);
        } catch (Exception ignored) {

        }
    }

}
