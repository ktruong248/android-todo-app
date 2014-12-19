package com.example.ktruong.todoapp.utils;


public final class StringUtils {

    public static boolean isEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

}
