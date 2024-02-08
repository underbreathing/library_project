package com.example.libraryproject;

import com.example.libraryproject.fxMethods.PasswordHasher;

public class Try {
    public static void main(String[] args) {
        String password = "Password";
        System.out.println(password);
        System.out.println(PasswordHasher.hashPassword(password));
        System.out.println(PasswordHasher.hashPassword(password));

    }
}
