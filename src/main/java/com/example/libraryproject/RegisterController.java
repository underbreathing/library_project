package com.example.libraryproject;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.libraryproject.db.DataBaseHandler;
import com.example.libraryproject.fxMethods.FxMethods;
import com.example.libraryproject.fxMethods.Toast;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;


public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonBackAuthorization;

    @FXML
    private Button buttonRegister;

    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fieldPassword;

    @FXML
    private TextField fieldFatherName;

    @FXML
    private TextField fieldFirstName;

    @FXML
    private TextField fieldNum;

    @FXML
    private TextField fieldSecondName;

    @FXML
    private TextField fieldSeries;

    @FXML
    void BackToAuthButtonHandler(ActionEvent event) {
        FxMethods.changeStageFxml(getClass().getResource("hello-view.fxml"),buttonBackAuthorization);
    }


    @FXML
    void initialize() {


        buttonRegister.setOnAction(event -> {
            signUpNewUser();
        });


    }

    private void signUpNewUser(){
        DataBaseHandler dbHandler = new DataBaseHandler();
        String firstName = fieldFirstName.getText();
        String lastName = fieldSecondName.getText();
        String fatherName = fieldFatherName.getText();
        String series = fieldSeries.getText();
        String num = fieldNum.getText();
        String login = fieldLogin.getText();
        String password = fieldPassword.getText();

        System.out.println(firstName + " " + lastName + " " + fatherName + " " + series + " " + num + " " + login + " " + password);
        try {
            if(dbHandler.signUpUser(firstName,lastName,fatherName,
                    series,num,login,password)){
                System.out.println("Регистрация прошла успешно!");
                Toast.makeToast(FxMethods.changeStageFxml(getClass().getResource("hello-view.fxml"),buttonBackAuthorization),"Регистрация прошла успешно",
                        Duration.seconds(2));

            }
        } catch (SQLException e) {
            Toast.makeToast((Stage) buttonRegister.getScene().getWindow(), "Клиент с таким паспортом уже зарегистрирован", Duration.seconds(2));
        }
    }

}


