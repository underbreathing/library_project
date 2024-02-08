package com.example.libraryproject.db;


import com.example.libraryproject.fxMethods.PasswordHasher;
import javafx.scene.control.TextArea;
import java.sql.*;

public class DataBaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        String connectionUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionUrl, dbUser, dbPass);
        return dbConnection;
    }



    public ResultSet getClientsPage(final int offset,final TextArea textArea) throws SQLException {
        String select = "SELECT * FROM " + Const.Clients.CLIENTS_TABLE + " LIMIT " + Const.OFFSET_STEP + " OFFSET ? ;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1,offset);
        textArea.appendText(prSt.toString() + "\n");
        return prSt.executeQuery();
    }

    public boolean updateClient(final String oldLogin,final String firstname, final String lastname, final String fatherName,
                             final String passportSeries, final String passportNum, final String newLogin, final String password)  {
        String insert = "INSERT INTO " + Const.Logins.LOGINS_TABLE + " (" + Const.Logins.LOGINS_LOGIN + "," + Const.Logins.LOGINS_PASSWORD +
        ") VALUES (?,?);";
        String update = "UPDATE " + Const.Clients.CLIENTS_TABLE + " SET " + Const.Clients.CLIENTS_FIRSTNAME + " = ?, " +
                Const.Clients.CLIENTS_LASTNAME + " = ?, " + Const.Clients.CLIENTS_FATHER_NAME + " = ?, " +
                Const.Clients.CLIENTS_PASSPORT_SERIES + " = ?, " + Const.Clients.CLIENTS_PASSPORT_NUM + " = ?, " + Const.Clients.CLIENTS_LOGIN + " = ? WHERE "
                +Const.Clients.CLIENTS_LOGIN + " = ?";
        String delete = "DELETE FROM " + Const.Logins.LOGINS_TABLE + " WHERE " + Const.Logins.LOGINS_LOGIN + " = ?;";

        Connection connection = null;
        try {
            connection = getDbConnection();
            connection.setAutoCommit(false);

            PreparedStatement prSt1 = getDbConnection().prepareStatement(insert);
            prSt1.setString(1,newLogin);
            prSt1.setString(2,password);


            PreparedStatement prSt2 = getDbConnection().prepareStatement(update);
            prSt2.setString(1,firstname);
            prSt2.setString(2,lastname);
            prSt2.setString(3,fatherName);
            prSt2.setString(4,passportSeries);
            prSt2.setString(5,passportNum);
            prSt2.setString(6,newLogin);
            prSt2.setString(7,oldLogin);


            PreparedStatement prSt3 = getDbConnection().prepareStatement(delete);
            prSt3.setString(1,oldLogin);

            prSt1.executeUpdate();
            prSt2.executeUpdate();
            prSt3.executeUpdate();

            //textArea.appendText(prSt1.toString() + "\n" + prSt2.toString() + "\n" + prSt3.toString() + "\n");
            connection.commit();
            return true;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ResultSet clientFineByLogin(final String login) throws SQLException {
        String select = "SELECT " + Const.calculateFineSumForClientLogin + "(?) as fine;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1,login);
        return prSt.executeQuery();
    }

    public ResultSet getUserData(final String login)  {
        String call = "CALL " + Const.dataForClient + "(?);";
        String select = "SELECT * FROM " + Const.dataClient.DATA_CLIENT_TABLE + ";";
        Connection connection = null;
        try {
            connection = getDbConnection();
            connection.setAutoCommit(false);

            PreparedStatement prSt1 = connection.prepareStatement(call);
            prSt1.setString(1, login);
            prSt1.executeUpdate();

            PreparedStatement prSt2 = connection.prepareStatement(select);
            ResultSet result = prSt2.executeQuery();

            connection.commit();
            return result;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateType(final String name,final int fine, final int dayCount,TextArea textArea) throws SQLException {
        String update = "UPDATE " + Const.BookTypes.BOOK_TYPES_TABLE + " SET " + Const.BookTypes.BOOK_TYPES_FINE +
                " = ? , " + Const.BookTypes.BOOK_TYPES_DAY_COUNT + " = ? " + " WHERE " + Const.BookTypes.BOOK_TYPES_NAME + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setInt(1,fine);
        prSt.setInt(2,dayCount);
        prSt.setString(3,name);
        prSt.executeUpdate();
        textArea.appendText(prSt.toString() + "\n");
    }

    public void deleteType(final String name,TextArea textArea) throws SQLException {
        String delete = "DELETE FROM " + Const.BookTypes.BOOK_TYPES_TABLE + " WHERE " + Const.BookTypes.BOOK_TYPES_NAME
                + " = ? ;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setString(1,name);
        prSt.executeUpdate();
        textArea.appendText(prSt.toString() + "\n");
    }

    public void updateBook(final String name, final int cnt,TextArea textArea) throws SQLException {
        String update = "UPDATE " + Const.Books.BOOKS_TABLE + " SET " + Const.Books.BOOKS_CNT + " = ? WHERE " +
                Const.Books.BOOKS_NAME + " = ? ;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setInt(1,cnt);
        prSt.setString(2,name);
        prSt.executeUpdate();
        textArea.appendText(prSt.toString() + "\n");
    }

    public void deleteBook(final String name,TextArea textArea) throws SQLException {
        String deleteBook = "DELETE FROM " + Const.Books.BOOKS_TABLE + " WHERE " + Const.Books.BOOKS_NAME + " = ? ;";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(deleteBook);
        preparedStatement.setString(1,name);
        preparedStatement.executeUpdate();
        textArea.appendText(preparedStatement.toString() + "\n");
    }

    public void addType(final String name,final int fine, final int dayCount,TextArea textArea) throws SQLException {
        String insert = "INSERT INTO " + Const.BookTypes.BOOK_TYPES_TABLE + " (" + Const.BookTypes.BOOK_TYPES_NAME +
               "," + Const.BookTypes.BOOK_TYPES_FINE + "," + Const.BookTypes.BOOK_TYPES_DAY_COUNT + ") VALUES(?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1,name);
        prSt.setInt(2,fine);
        prSt.setInt(3,dayCount);
        prSt.executeUpdate();
        textArea.appendText(prSt.toString() + "\n");
    }

    public void addBook(final int type_id, final String name, final int cnt,TextArea textArea) throws SQLException {
        String insert = "INSERT INTO " + Const.Books.BOOKS_TABLE + " (" + Const.Books.BOOKS_TYPE_ID + "," +
                Const.Books.BOOKS_NAME + "," + Const.Books.BOOKS_CNT + ") VALUES (?,?,?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setInt(1,type_id);
        prSt.setString(2,name);
        prSt.setInt(3,cnt);
        prSt.executeUpdate();
        textArea.appendText(prSt.toString() + "\n");
    }

    public void addBook(final String name, final int cnt,TextArea textArea) throws SQLException {
        String call = "CALL " + Const.addBookProcedure + "(?,?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(call);
        prSt.setString(1,name);
        prSt.setInt(2,cnt);
        prSt.executeUpdate();
        textArea.appendText(prSt.toString() + "\n");
    }

    public void acceptBook(final int bookId, final String passportNum,TextArea textArea) throws SQLException {
        String call = "CALL " + Const.acceptBookProcedure + "(?,?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(call);
            prSt.setInt(1,bookId);
            prSt.setString(2,passportNum);
            prSt.executeUpdate();
            textArea.appendText(prSt.toString() + "\n");
    }

    public int getFineSumForClient(final String passportNum,TextArea textArea){
        String select = "SELECT " + Const.calculateFineSumForClient + "(?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,passportNum);
            textArea.appendText(prSt.toString() + "\n");
            ResultSet result = prSt.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet getBook(final String name,TextArea textArea){
        String select = "SELECT * FROM " + Const.Books.BOOKS_TABLE
                + " WHERE name = ?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,name);
            textArea.appendText(prSt.toString() + "\n");
            return prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPageOfTypes(TextArea textArea){
        String select = "SELECT * FROM " + Const.BookTypes.BOOK_TYPES_TABLE + ";";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            textArea.appendText(prSt.toString() + "\n");
            return prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPageOfBook(final int offset,final TextArea textArea){
        String select = "SELECT * FROM " + Const.Books.BOOKS_TABLE
                + " LIMIT " + Const.OFFSET_STEP + " OFFSET ?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1,offset);
            textArea.appendText(prSt.toString() + "\n");
            return prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPageOfBase(final int offset,TextArea textArea){
        String select = "SELECT * FROM " + Const.Journal.JOURNAL_TABLE + " ORDER BY " + Const.Journal.JOURNAL_DATE_BEG + " DESC "
                + " LIMIT " + Const.OFFSET_STEP + " OFFSET ?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1,offset);
            textArea.appendText(prSt.toString() + "\n");
            return prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertJournal(final String passportNum, final int bookId,TextArea textArea) throws SQLException {
        String insert = "CALL " + Const.deliveryBookProcedure + "(?,?);";
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1,bookId);
            prSt.setString(2,passportNum);
            prSt.executeUpdate();
            textArea.appendText(prSt.toString() + "\n");
    }

    public ResultSet getBookById(final int bookId,TextArea textArea){
        String selectBookId = "SELECT * FROM " + Const.Books.BOOKS_TABLE + " WHERE id = ?;";
        try {
            PreparedStatement prStBookId = getDbConnection().prepareStatement(selectBookId);
            prStBookId.setInt(1,bookId);
            textArea.appendText(prStBookId.toString() + "\n");
            return  prStBookId.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getClientByPassport(final String passportNum,TextArea textArea){

        String selectPassport = "SELECT * FROM " + Const.Clients.CLIENTS_TABLE + " WHERE " + Const.Clients.CLIENTS_PASSPORT_NUM
                + " = ?;";
        try {
            PreparedStatement prStPassport = getDbConnection().prepareStatement(selectPassport);
            prStPassport.setString(1,passportNum);
            textArea.appendText(prStPassport.toString() + "\n");
            return prStPassport.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getLogin(final String login, final String password) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.Logins.LOGINS_TABLE + " WHERE " +
                Const.Logins.LOGINS_LOGIN + " = ?";
        //" = ? AND " + Const.Logins.LOGINS_PASSWORD + " = ?"
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
           // prSt.setString(2, password);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //надо добавить обработку ошибок на неправильный ввод
    //протестировать на возврат false
    public boolean signUpUser(final String firstname, final String lastname, final String fatherName,
                              final String passportSeries, final String passportNum, final String login, final String password) throws SQLException {

        String insertLogins = "INSERT INTO logins (login,password) VALUES(?,?);";

        String insertClients = "INSERT INTO " + Const.Clients.CLIENTS_TABLE + " (" +
                Const.Clients.CLIENTS_FIRSTNAME + "," + Const.Clients.CLIENTS_LASTNAME + "," +
                Const.Clients.CLIENTS_FATHER_NAME + "," + Const.Clients.CLIENTS_PASSPORT_SERIES + ","
                + Const.Clients.CLIENTS_PASSPORT_NUM + "," + Const.Clients.CLIENTS_LOGIN + ") " + "VALUES(?,?,?,?,?,?);";


        Connection connection = null;
        try {
            connection = getDbConnection();
            connection.setAutoCommit(false);

            PreparedStatement prSt1 = connection.prepareStatement(insertLogins);
            prSt1.setString(1, login);
            prSt1.setString(2, PasswordHasher.hashPassword(password));
            prSt1.executeUpdate();

            PreparedStatement prSt2 = connection.prepareStatement(insertClients);
            prSt2.setString(1, firstname);
            prSt2.setString(2, lastname);
            prSt2.setString(3, fatherName);
            prSt2.setString(4, passportSeries);
            prSt2.setString(5, passportNum);
            prSt2.setString(6,login);
            prSt2.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if(e.getMessage().contains("RAISE")){
                throw e;
            }
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;

    }

//    public ArrayList<JournalNote> getJournalPage(){
//        String select = "SELECT * from"
//    }
}
