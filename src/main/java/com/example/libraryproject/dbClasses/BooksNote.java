package com.example.libraryproject.dbClasses;



public class BooksNote {
    public int id;
    public int type_id;
    public String name;
    public int cnt;

    public BooksNote(int id, int type_id, String name, int cnt) {
        this.id = id;
        this.type_id = type_id;
        this.name = name;
        this.cnt = cnt;
    }
}
