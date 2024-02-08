package com.example.libraryproject.dbClasses;

public class Client {
    private final String firstName;
    private final String lastName;
    private final String fatherName;
    private final String passportSeries;
    private final String passportNum;

    public Client(final String firstName, final String lastName, final String fatherName,
                  final String passportSeries, final String passportNum){
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.passportSeries = passportSeries;
        this.passportNum = passportNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public String getPassportNum() {
        return passportNum;
    }
}
