package com.api.auth.api.authenticator.util;

public class CPFValidator {

    public static void isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF inválido!");
        }
    }
}
