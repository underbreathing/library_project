package com.example.libraryproject.fxMethods;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hashPassword(String password) {
        // Генерируем соль
        String salt = BCrypt.gensalt();
        // Хешируем пароль с помощью соли
        String hashedPassword = BCrypt.hashpw(password, salt);
        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        // Сравниваем введенный пароль с хешем
        boolean matches = BCrypt.checkpw(password, hashedPassword);
        return matches;
    }
}