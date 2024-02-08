package com.example.libraryproject.dbClasses;

public class LoginData {
    private final String login;
    private final String password;

    public LoginData(final String login, final String password){
        this.login = login;
        this.password = password;
    }

    public static LoginData currentLogin;

    public static void setCurrentLogin(final LoginData loginData){
        currentLogin = loginData;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
