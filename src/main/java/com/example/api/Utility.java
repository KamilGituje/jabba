package com.example.api;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class Utility {

    public static boolean hasOnlyLetters(String string) {
        if (StringUtils.hasText(string)) {
            for (int i = 0; i < string.length(); i++) {
                if (!Character.isLetter(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasOnlyDigits(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}