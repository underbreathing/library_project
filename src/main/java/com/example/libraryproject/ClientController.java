package com.example.libraryproject;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.libraryproject.db.Const;
import com.example.libraryproject.db.DataBaseHandler;
import com.example.libraryproject.dbClasses.ClientDataNote;
import com.example.libraryproject.dbClasses.LoginData;
import com.example.libraryproject.fxMethods.FxMethods;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClientController {


    @FXML
    private TableColumn<ClientDataNote, String> columnBook;

    @FXML
    private TableColumn<ClientDataNote, String> columnRest;

    @FXML
    private Label labelFine;

    @FXML
    private Label labelNameClient;

    @FXML
    private Label labelYourBooks;

    @FXML
    private TableView<ClientDataNote> tableClientData;

    @FXML
    private Button buttonUpdateAcc;

    @FXML
    private Button buttonBackClient;

    @FXML
    void initialize() {
        String login = LoginData.currentLogin.getLogin();
        labelNameClient.setText(login);
        setUserData(login);
        labelFine.setText(String.valueOf(getClientFine(login)));

        buttonUpdateAcc.setOnAction(event ->{
            FxMethods.changeStageFxml(getClass().getResource("update-client-view.fxml"),buttonUpdateAcc);
        });

        buttonBackClient.setOnAction(event ->{
            FxMethods.changeStageFxml(getClass().getResource("hello-view.fxml"),buttonBackClient);
        });
    }


    private int getClientFine(final String login){
        DataBaseHandler dbHandler = new DataBaseHandler();
        try {
            ResultSet resultSet = dbHandler.clientFineByLogin(login);
            if(resultSet!= null){
                if(resultSet.next()){
                    return resultSet.getInt("fine");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    private void setUserData(final String login){
        ArrayList<ClientDataNote> clientDataNotes = getUserData(login);
        if(clientDataNotes != null) {
            ObservableList<ClientDataNote> notes = FXCollections.observableArrayList(clientDataNotes);
            columnBook.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name));
            columnRest.setCellValueFactory(data -> {
                if(data.getValue().rest < 0){
                    return new SimpleStringProperty("просрочена на " + Math.abs(data.getValue().rest) + " дн.");
                }else {
                    String stringRest = String.valueOf(data.getValue().rest);
                    return new SimpleStringProperty(stringRest);
                }
            });
            tableClientData.setItems(notes);
        }
    }

    private ArrayList<ClientDataNote> getUserData(final String login){
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getUserData(login);
        ArrayList<ClientDataNote> clientDataNotes = new ArrayList<>();
        if(resultSet != null){
            try {
                while (resultSet.next()) {
                    clientDataNotes.add(new ClientDataNote(resultSet.getString(Const.dataClient.DATA_CLIENT_NAME),
                            resultSet.getInt(Const.dataClient.DATA_CLIENT_REST)));
                }
                return clientDataNotes;
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

}
