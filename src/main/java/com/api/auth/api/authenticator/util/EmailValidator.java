package com.api.auth.api.authenticator.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static void isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new IllegalArgumentException("E-mail inválido!");
        }
    }
}