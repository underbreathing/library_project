package com.example.libraryproject;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.libraryproject.animations.Shake;
import com.example.libraryproject.db.Const;
import com.example.libraryproject.db.DataBaseHandler;
import com.example.libraryproject.dbClasses.BooksNote;
import com.example.libraryproject.dbClasses.ClientsNote;
import com.example.libraryproject.dbClasses.JournalNote;
import com.example.libraryproject.dbClasses.TypeNote;
import com.example.libraryproject.fxMethods.ExelExporter;
import com.example.libraryproject.fxMethods.FxMethods;
import com.example.libraryproject.fxMethods.Toast;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminMainController {

    private static int currentOffsetJournal = 0;
    private static int currentOffsetBooks = 0;
    private static int currentOffsetClients = 0;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonDelivery;

    @FXML
    private TableColumn<JournalNote, Integer> columnBookId;

    @FXML
    private TableColumn<JournalNote, Integer> columnCliendId;

    @FXML
    private TableColumn<JournalNote, String> columnDateBeg;

    @FXML
    private TableColumn<JournalNote, String> columnDateEnd;

    @FXML
    private TableColumn<JournalNote, String> columnDateRet;

    @FXML
    private TableColumn<JournalNote, Integer> columnId;

    @FXML
    private TableView<JournalNote> tableBase;

    @FXML
    private TextField fieldBookId;

    @FXML
    private TextField fieldPassportNum;

    @FXML
    private Button buttonBackBase;

    @FXML
    private Button buttonThenBase;

    @FXML
    private Button buttonCalculateFine;

    @FXML
    private TextField fieldPassportNumAccept;

    @FXML
    private TextField fieldBookIdAccept;

    @FXML
    private Label labelFineSum;

    @FXML
    private Button buttonAccept;

    @FXML
    private TableColumn<BooksNote, Integer> columnIdBooks;

    @FXML
    private TableColumn<BooksNote, Integer> columnTypeIdBooks;

    @FXML
    private TableColumn<BooksNote, String> columnNameBooks;

    @FXML
    private TableColumn<BooksNote, Integer> columnCountBooks;

    @FXML
    private TableView<BooksNote> tableBooks;

    @FXML
    private Button buttonNextBooks;

    @FXML
    private Button buttonBackBooks;

    @FXML
    private TextField textFieldBooks;

    @FXML
    private Button buttonAddBook;

    @FXML
    private Label labelBookType;

    @FXML
    private TextField fieldNameBook;

    @FXML
    private TextField fieldBookCount;

    @FXML
    private CheckBox checkBoxBookNew;

    @FXML
    private TextField fieldBookType;

    @FXML
    private TableColumn<TypeNote,String> columnTypeName;

    @FXML
    private TableColumn<TypeNote,Integer> columnTypeFine;

    @FXML
    private TableColumn<TypeNote,Integer> columnTypeDayCount;

    @FXML
    private TableView<TypeNote> tableBookTypes;

    @FXML
    private TextField fieldNameType;

    @FXML
    private TextField fieldFine;

    @FXML
    private TextField fieldDayCount;

    @FXML
    private Button buttonAddType;

    @FXML
    private Button buttonDeleteBook;

    @FXML
    private Button buttonUpdateBook;

    @FXML
    private Button buttonDeleteType;

    @FXML
    private Button buttonUpdateType;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonToExelJournal;

    @FXML
    private Button buttonExelBooks;

    @FXML
    private TableColumn<ClientsNote,Integer> columnIdClients;

    @FXML
    private TableColumn<ClientsNote,String> columnFirstNameClients;

    @FXML
    private TableColumn<ClientsNote,String> columnLastNameClients;

    @FXML
    private TableColumn<ClientsNote,String> columnFatherNameClients;

    @FXML
    private TableColumn<ClientsNote,String> columnSeries;

    @FXML
    private TableColumn<ClientsNote,String> columnNum;

    @FXML
    private TableColumn<ClientsNote,String> columnLogin;

    @FXML
    private TableView<ClientsNote> tableClients;

    @FXML
    private Button buttonNextClients;

    @FXML
    private Button buttonBackClients;

    @FXML
    private Button buttonToExelClients;

   @FXML
   private TextArea textAreaHistory;

    @FXML
    void initialize() {

        setJournalPage(currentOffsetJournal);
        setBooksPage(currentOffsetBooks);
        setClientsPage(currentOffsetClients);
        setTypesPage();



        buttonToExelClients.setOnAction(event ->{
            new ExelExporter().exportToExcel(tableBooks,"C:\\Users\\artem\\Desktop\\выгрузка.xlsx");
        });

        buttonBackClients.setOnAction(event ->{
            currentOffsetClients -= Const.OFFSET_STEP;
            setClientsPage(currentOffsetClients);
            if(currentOffsetClients == 0){
                buttonBackClients.setVisible(false);
            }
        });

        buttonNextClients.setOnAction(event ->{
            currentOffsetClients += Const.OFFSET_STEP;
            setClientsPage(currentOffsetClients);
            buttonBackClients.setVisible(true);
        });

        buttonExelBooks.setOnAction(event->{
            new ExelExporter().exportToExcel(tableBooks,"C:\\Users\\artem\\Desktop\\выгрузка.xlsx");
        });

        buttonToExelJournal.setOnAction(event ->{
            new ExelExporter().exportToExcel(tableBase,"C:\\Users\\artem\\Desktop\\выгрузка.xlsx");
        });

        buttonExit.setOnAction(event ->{
            FxMethods.changeStageFxml(getClass().getResource("hello-view.fxml"),buttonExit);
        });

        buttonDeleteType.setOnAction(event -> {
            String name = fieldNameType.getText();
            if(!name.isEmpty()){
                deleteType(name);
            }
        });

        buttonUpdateType.setOnAction(event -> {
            String name = fieldNameType.getText();
            String fine = fieldFine.getText();
            String dayCount = fieldDayCount.getText();
            if(!name.isEmpty() && !fine.isEmpty() && ! dayCount.isEmpty()){
                updateType(name, Integer.parseInt(fine),Integer.parseInt(dayCount));
            }
        });

        buttonUpdateBook.setOnAction(event -> {
            String name = fieldNameBook.getText();
            String cnt = fieldBookCount.getText();
            if(!name.isEmpty() && !cnt.isEmpty()){
                updateBook(name, Integer.parseInt(cnt));
            }
        });

        buttonDeleteBook.setOnAction(event -> {
            String name = fieldNameBook.getText();
            if(!name.isEmpty()){
                deleteBook(name);
            }
        });

        buttonDelivery.setOnAction(event ->{
            String passportNum = fieldPassportNum.getText();
            String bookId = fieldBookId.getText();
            if(!passportNum.isEmpty() && !bookId.isEmpty()){
                checkFieldsAndDelivery(passportNum, Integer.parseInt(bookId));//parseInt. могут быть ошибки если не int
            }
            else{
                System.out.println("не все поля для вставки заполнены");
                animDelivery();
            }
        });

        buttonThenBase.setOnAction(event ->{
            currentOffsetJournal += Const.OFFSET_STEP;
            setJournalPage(currentOffsetJournal);
            buttonBackBase.setVisible(true);
        });

        buttonBackBase.setOnAction(event ->{
            currentOffsetJournal -= Const.OFFSET_STEP;
            setJournalPage(currentOffsetJournal);
            if(currentOffsetJournal == 0){
                buttonBackBase.setVisible(false);
            }
        });

        buttonCalculateFine.setOnAction(event ->{
            String passportNum = fieldPassportNumAccept.getText();
            String bookId = fieldBookIdAccept.getText();
            checkFieldsExistence(passportNum,bookId);
            labelFineSum.setText(String.valueOf(calculateFineSum(passportNum)));
                });

        buttonAccept.setOnAction(event -> {
            acceptBook();
        });

        buttonNextBooks.setOnAction(event ->{
            currentOffsetBooks += Const.OFFSET_STEP;
            setBooksPage(currentOffsetBooks);
            buttonBackBooks.setVisible(true);
        });

        buttonBackBooks.setOnAction(event ->{
            currentOffsetBooks -= Const.OFFSET_STEP;
            setBooksPage(currentOffsetBooks);
            if(currentOffsetBooks == 0){
                buttonBackBooks.setVisible(false);
            }
        });

        textFieldBooks.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() >= 1) {
                if (newValue.charAt(newValue.length() - 1) == '.') {
                    setFoundedBook(newValue.substring(0, newValue.length() - 1));
                }
            }
            else if(newValue.trim().isEmpty() && oldValue.length() == 1){
                setBooksPage(currentOffsetBooks);
            }
        }));

        buttonAddBook.setOnAction(event ->{
            String name = fieldNameBook.getText();
            String cnt = fieldBookCount.getText();
            if(!name.isEmpty() && ! cnt.isEmpty()){
                if(checkBoxBookNew.isSelected()){
                    String type = fieldBookType.getText();
                    if(!type.isEmpty()){
                        addBook(Integer.parseInt(type),name,Integer.parseInt(cnt));
                    }
                }
                else{
                    addBook(name, Integer.parseInt(cnt));
                }
            }
        });

        checkBoxBookNew.setOnAction(event ->{
            if(checkBoxBookNew.isSelected()){
                labelBookType.setVisible(true);
                fieldBookType.setVisible(true);
            }
            else{
                labelBookType.setVisible(false);
                fieldBookType.setVisible(false);
            }
        });

        buttonAddType.setOnAction(event -> {
            String name = fieldNameType.getText();
            String fine = fieldFine.getText();
            String dayCount = fieldDayCount.getText();
            if(!name.isEmpty() && !fine.isEmpty() && ! dayCount.isEmpty()){
                addType(name,Integer.parseInt(fine),Integer.parseInt(dayCount));
            }
        });

    }




    private void deleteType(final String name){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try{
            dbHandler.deleteType(name,textAreaHistory);
            fieldNameType.clear();
            Toast.makeToast((Stage) buttonDeleteType.getScene().getWindow(), "Тип удален", Duration.seconds(2));
            setTypesPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateType(final String name, final int fine, final int dayCount){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try{
            dbHandler.updateType(name, fine, dayCount,textAreaHistory);
            fieldNameType.clear();
            fieldFine.clear();
            fieldDayCount.clear();
            Toast.makeToast((Stage) buttonDeleteType.getScene().getWindow(), "Тип обновлен", Duration.seconds(2));
            setTypesPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBook(final String name, final int cnt){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try{
            dbHandler.updateBook(name,cnt,textAreaHistory);
            fieldNameBook.clear();
            fieldBookCount.clear();
            Toast.makeToast((Stage) buttonUpdateBook.getScene().getWindow(), "Данные обновлены", Duration.seconds(2));
            setBooksPage(currentOffsetBooks);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook(final String name){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            dbHandler.deleteBook(name,textAreaHistory);
            fieldNameBook.clear();
            setBooksPage(currentOffsetBooks);
            Toast.makeToast((Stage) buttonDeleteBook.getScene().getWindow(), "Книга успешно удалена", Duration.seconds(2));
        } catch (SQLException e) {
            if(e.getMessage().contains(Const.Triggers.STOP_DELETE_UNRETURNED)){
                Toast.makeToast((Stage) buttonDeleteBook.getScene().getWindow(), "Книга кем то не возвращена", Duration.seconds(2));
            }
        }
    }



    private void addType(final String name, final int fine, final int dayCount){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try{
            dbHandler.addType(name,fine,dayCount,textAreaHistory);
            fieldFine.clear();
            fieldNameType.clear();
            fieldDayCount.clear();
            Toast.makeToast((Stage) buttonAddType.getScene().getWindow(), "Тип успешно добавлен", Duration.seconds(2));
            setTypesPage();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void addBook(final String name, final int cnt){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            dbHandler.addBook(name,cnt,textAreaHistory);
            fieldNameBook.clear();
            fieldBookCount.clear();
            Toast.makeToast((Stage) buttonAddBook.getScene().getWindow(), "Книги успешно добавлены", Duration.seconds(2));
            setBooksPage(currentOffsetBooks);
        } catch (SQLException e) {
            if(e.getMessage().contains("RAISE")){
                Toast.makeToast((Stage) buttonAddBook.getScene().getWindow(), "Такой книги не найдено.", Duration.seconds(2));
            }
        }
    }

    private void addBook(final int type, final String name, final int cnt){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            dbHandler.addBook(type,name,cnt,textAreaHistory);
            fieldNameBook.clear();
            fieldBookCount.clear();
            fieldBookType.clear();
            Toast.makeToast((Stage) buttonAddBook.getScene().getWindow(), "Книги успешно добавлены", Duration.seconds(2));
            setBooksPage(currentOffsetBooks);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void acceptBook(){
        String passportNum = fieldPassportNumAccept.getText();
        String bookId = fieldBookIdAccept.getText();
        checkFieldsExistence(passportNum,bookId);
        try {
            acceptBook(Integer.parseInt(bookId),passportNum);
            fieldPassportNum.clear();
            fieldBookIdAccept.clear();
            Toast.makeToast((Stage) buttonAccept.getScene().getWindow(), "Книга успешно возвращена", Duration.seconds(2));
            setJournalPage(currentOffsetJournal);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("RAISE")){
                Toast.makeToast((Stage) buttonAccept.getScene().getWindow(), "Такой записи в таблице не найдено", Duration.seconds(2));
            }
        }
    }

    private boolean checkFieldsExistence(final String passportNum, final String bookId){
        if(!passportNum.isEmpty() && !bookId.isEmpty()){
            return checkFields(passportNum, Integer.parseInt(bookId));
        }
        else{
            animAccept();
        }
        return false;
    }

    private void acceptBook(final int bookId, final String passportNum) throws SQLException {
        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.acceptBook(bookId,passportNum,textAreaHistory);
    }

    private int calculateFineSum(final String passportNum){
        DataBaseHandler dbHandler = new DataBaseHandler();
        int result = dbHandler.getFineSumForClient(passportNum,textAreaHistory);
        System.out.println(result);
        return result;
    }

    private void setFoundedBook(final String name){
        ArrayList<BooksNote> booksNotes = getFoundedBook(name);
        if(booksNotes != null){
            ObservableList<BooksNote> noteItems = FXCollections.observableArrayList(booksNotes);
            columnIdBooks.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().id).asObject());
            columnTypeIdBooks.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().type_id).asObject());
            columnCountBooks.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().cnt).asObject());
            columnNameBooks.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name));

            tableBooks.setItems(noteItems);
        }
    }

    private void setTypesPage(){
        ArrayList<TypeNote> typeNotes = getPageOfTypes();
        if(typeNotes != null){
            ObservableList<TypeNote> noteItems = FXCollections.observableArrayList(typeNotes);

            columnTypeName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name));
            columnTypeFine.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().fine).asObject());
            columnTypeDayCount.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().dayCount).asObject());

            tableBookTypes.setItems(noteItems);
        }
    }

    private void setBooksPage(final int offset) {
        ArrayList<BooksNote> booksNotes = getPageOfBooks(offset);
        if(booksNotes != null){
            ObservableList<BooksNote> noteItems = FXCollections.observableArrayList(booksNotes);
            columnIdBooks.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().id).asObject());
            columnTypeIdBooks.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().type_id).asObject());
            columnCountBooks.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().cnt).asObject());
            columnNameBooks.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name));

            tableBooks.setItems(noteItems);
        }

    }

    private void setJournalPage(final int offset){
        ArrayList<JournalNote> journalNotes = getJournalPage(offset);
        if(journalNotes != null){
            ObservableList<JournalNote> noteItems = FXCollections.observableArrayList(journalNotes);
            columnId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().id).asObject());
            columnBookId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().book_id).asObject());
            columnCliendId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().client_id).asObject());
            columnDateBeg.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().date_beg.toString()));
            columnDateEnd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().date_end.toString()));
            columnDateRet.setCellValueFactory(data -> data.getValue().getDateRetProperty());

            tableBase.setItems(noteItems);
        }
    }

    private void setClientsPage(final int offset){
        ArrayList<ClientsNote> clientsNotes = getClientsPage(offset);
        if(clientsNotes != null){
            ObservableList<ClientsNote> noteItems = FXCollections.observableArrayList(clientsNotes);
            columnIdClients.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().id).asObject());
            columnFirstNameClients.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().firstName));
            columnLastNameClients.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().lastName));
            columnFatherNameClients.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().fatherName));
            columnSeries.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().series));
            columnNum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().num));
            columnLogin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().login));

            tableClients.setItems(noteItems);
        }
    }

    private ArrayList<ClientsNote> getClientsPage(final int offset){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            ResultSet resultSet = dbHandler.getClientsPage(offset,textAreaHistory);
            ArrayList<ClientsNote> clientsNotes = new ArrayList<>();
            if(resultSet != null){
                while(resultSet.next()){
                    clientsNotes.add(new ClientsNote(resultSet.getInt("id"),resultSet.getString(Const.Clients.CLIENTS_FIRSTNAME),
                            resultSet.getString(Const.Clients.CLIENTS_LASTNAME),resultSet.getString(Const.Clients.CLIENTS_FATHER_NAME),
                            resultSet.getString(Const.Clients.CLIENTS_PASSPORT_SERIES),resultSet.getString(Const.Clients.CLIENTS_PASSPORT_NUM),
                            resultSet.getString(Const.Clients.CLIENTS_LOGIN)));
                }
                return clientsNotes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<BooksNote> getFoundedBook(final String name){
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getBook(name,textAreaHistory);
        ArrayList<BooksNote> booksNotes = new ArrayList<>();
        try {
            while (resultSet.next()) {
                booksNotes.add(new BooksNote(resultSet.getInt("id"),resultSet.getInt(Const.Books.BOOKS_TYPE_ID),
                        resultSet.getString(Const.Books.BOOKS_NAME),resultSet.getInt(Const.Books.BOOKS_CNT)));
            }
            return booksNotes;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    private  ArrayList<TypeNote> getPageOfTypes(){
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getPageOfTypes(textAreaHistory);
        ArrayList<TypeNote> typeNotes = new ArrayList<>();
        try{
            while (resultSet.next()){
                typeNotes.add(new TypeNote(resultSet.getString(Const.BookTypes.BOOK_TYPES_NAME),resultSet.getInt(Const.BookTypes.BOOK_TYPES_FINE),
                        resultSet.getInt(Const.BookTypes.BOOK_TYPES_DAY_COUNT)));
            }
            return typeNotes;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<BooksNote> getPageOfBooks(final int offset){
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getPageOfBook(offset,textAreaHistory);
        ArrayList<BooksNote> booksNotes = new ArrayList<>();
        try {
            while (resultSet.next()) {
                booksNotes.add(new BooksNote(resultSet.getInt("id"),resultSet.getInt(Const.Books.BOOKS_TYPE_ID),
                        resultSet.getString(Const.Books.BOOKS_NAME),resultSet.getInt(Const.Books.BOOKS_CNT)));
            }
            return booksNotes;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<JournalNote> getJournalPage(final int offset){
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getPageOfBase(offset,textAreaHistory);
        ArrayList<JournalNote> journalNotes = new ArrayList<>();
        try {
            while(resultSet.next()){
                journalNotes.add(new JournalNote(resultSet.getInt("id"),resultSet.getInt(Const.Journal.JOURNAL_CLIENT_ID),
                        resultSet.getInt(Const.Journal.JOURNAL_BOOK_ID),resultSet.getDate(Const.Journal.JOURNAL_DATE_BEG),
                        resultSet.getDate(Const.Journal.JOURNAL_DATE_END),resultSet.getDate(Const.Journal.JOURNAL_DATE_RET)));
            }
            return journalNotes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void delivery(final String passportNum, final int bookId) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            dbHandler.insertJournal(passportNum, bookId,textAreaHistory);
            fieldBookId.clear();
            fieldPassportNum.clear();
            Toast.makeToast((Stage) buttonDelivery.getScene().getWindow(), "Книга успешно отдана ✅", Duration.seconds(2));
            setJournalPage(currentOffsetJournal);//обновляем информацию в таблице
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains(Const.Triggers.UPDATE_BOOK_CNT)) {
                Toast.makeToast((Stage) buttonDelivery.getScene().getWindow(), "Эти книги кончились", Duration.seconds(2));
            }
        }
    }

    private void checkFieldsAndDelivery(final String passportNum, final int bookId){
        if(checkFields(passportNum,bookId)) {
            delivery(passportNum, bookId);
        }
    }

    private boolean checkFields(final String passportNum, final int bookId) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet client = dbHandler.getClientByPassport(passportNum,textAreaHistory);//может вернуть null
        ResultSet book = dbHandler.getBookById(bookId,textAreaHistory);
        try {
            if (client.next()) {
                System.out.println("Клиент такой есть");
                if (book.next()) {
                    System.out.println("Все корректно");
                    return true;
                } else {
                    animDelivery();
                    Toast.makeToast((Stage) buttonDelivery.getScene().getWindow(), "Книги с таким id нет в базе.", Duration.seconds(2));
                }
            } else {
                animDelivery();
                Toast.makeToast((Stage) buttonDelivery.getScene().getWindow(), "Клиент с таким паспортом не найден.", Duration.seconds(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }


    private void animDelivery(){
        Shake passportNumAnim = new Shake(fieldPassportNum);
        Shake bookIdAnim = new Shake(fieldBookId);
        passportNumAnim.playAnim();
        bookIdAnim.playAnim();
    }

    private void animAccept(){
        Shake passportNumAnim = new Shake(fieldPassportNumAccept);
        Shake bookIdAnim = new Shake(fieldBookIdAccept);
        passportNumAnim.playAnim();
        bookIdAnim.playAnim();
    }


}
