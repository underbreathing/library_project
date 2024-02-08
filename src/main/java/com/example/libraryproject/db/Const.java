package com.example.libraryproject.db;

public class Const {

    public static final int OFFSET_STEP = 10;
    public static final String deliveryBookProcedure = "delivery_book";
    public static final String calculateFineSumForClient = "calculate_client_fine";
    public static final String calculateFineSumForClientLogin = "calculate_client_fine_login";
    public static final String acceptBookProcedure = "accept_book";
    public static final String addBookProcedure = "add_book";
    public static final String dataForClient = "data_for_client";


    public static final class dataClient{
        public static final String DATA_CLIENT_TABLE = "data_client";
        public static final String DATA_CLIENT_NAME = "book_name";
        public static final String DATA_CLIENT_REST = "rest";
    }





    public static final class Triggers{
        public static final String UPDATE_BOOK_CNT = "check_book_cnt_before_insert_journal";
        public static final String STOP_DELETE_UNRETURNED = "stop_delete_unreturned_book";

    }

    public static final class Logins{
        public static final String LOGINS_TABLE = "logins";
        public static final String LOGINS_LOGIN = "login";
        public static final String LOGINS_PASSWORD = "password";
    }
    public static final class Clients{
        public static final String CLIENTS_TABLE = "clients";
        public static final String CLIENTS_FIRSTNAME = "first_name";
        public static final String CLIENTS_LASTNAME = "last_name";
        public static final String CLIENTS_FATHER_NAME = "father_name";
        public static final String CLIENTS_PASSPORT_SERIES = "passport_series";
        public static final String CLIENTS_PASSPORT_NUM = "passport_num";
        public static final String CLIENTS_LOGIN = "login";
    }

    public static final class Books{
        public static final String BOOKS_TABLE = "books";
        public static final String BOOKS_TYPE_ID = "type_id";
        public static final String BOOKS_NAME = "name";
        public static final String BOOKS_CNT = "cnt";
    }

    public static final class Journal{
        public static final String JOURNAL_TABLE = "journal";
        public static final String JOURNAL_CLIENT_ID = "client_id";
        public static final String JOURNAL_BOOK_ID = "book_id";
        public static final String JOURNAL_DATE_BEG = "date_beg";
        public static final String JOURNAL_DATE_END = "date_end";
        public static final String JOURNAL_DATE_RET = "date_ret";
    }

    public static final class BookTypes{
        public static final String BOOK_TYPES_TABLE = "book_types";
        public static final String BOOK_TYPES_NAME = "name";
        public static final String BOOK_TYPES_FINE = "fine";
        public static final String BOOK_TYPES_DAY_COUNT = "day_count";
    }

}
