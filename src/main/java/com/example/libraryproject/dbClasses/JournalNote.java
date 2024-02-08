package com.example.libraryproject.dbClasses;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class JournalNote {
    public int id;
    public int client_id;
    public int book_id;
    public Date date_beg;
    public Date date_end;
    public Date date_ret;

    public SimpleStringProperty getDateRetProperty(){
        if(date_ret == null){
            return new SimpleStringProperty("null");
        }
        else{
            return new SimpleStringProperty(date_ret.toString());
        }
    }

    public JournalNote(int id, int client_id, int book_id, Date date_beg, Date date_end, Date date_ret) {
        this.id = id;
        this.client_id = client_id;
        this.book_id = book_id;
        this.date_beg = date_beg;
        this.date_end = date_end;
        this.date_ret = date_ret;
    }
}
