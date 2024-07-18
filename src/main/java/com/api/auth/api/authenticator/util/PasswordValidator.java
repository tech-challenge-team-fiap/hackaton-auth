package com.api.auth.api.authenticator.util;

public class PasswordValidator {
    public static void validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        if (password == null || !password.matches(regex)) {
            throw new IllegalArgumentException("Formato de Senha inválido, senha deve conter:" +
                    "- Pelo menos 8 caracteres\n" +
                    "- Pelo menos uma letra minúscula\n" +
                    "- Pelo menos uma letra maiúscula\n" +
                    "- Pelo menos um dígito\n" +
                    "- Pelo menos um caractere especial (ex: @#$%^&+=)!");
        }
    }
}
