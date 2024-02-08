package com.example.libraryproject.dbClasses;

public class ClientsNote {
    final public int id;
    final public String firstName;
    final public String lastName;
    final public String fatherName;
    final public String series;
    final public String num;
    final public String login;

    public ClientsNote(int id, String firstName, String lastName, String fatherName, String series, String num, String login) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.series = series;
        this.num = num;
        this.login = login;
    }
}
