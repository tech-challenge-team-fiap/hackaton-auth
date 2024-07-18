package com.api.auth.api.authenticator.util;

public class CRMValidator {

    public static void validateCRM(String crm) {
        if (crm.isEmpty() || crm == "" || !crm.matches("\\d{5}")) {
            throw new IllegalArgumentException("CRM inválido!");
        }
    }
}