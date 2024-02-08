package com.example.libraryproject;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.libraryproject.db.DataBaseHandler;
import com.example.libraryproject.dbClasses.LoginData;
import com.example.libraryproject.fxMethods.FxMethods;
import com.example.libraryproject.fxMethods.Toast;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UpdateClientController {



    @FXML
    private Button buttonBackUser;

    @FXML
    private Button buttonUpdateUser;

    @FXML
    private Button buttonDeleteAcc;

    @FXML
    private TextField fieldFatherName;

    @FXML
    private TextField fieldFirstName;

    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fieldNum;

    @FXML
    private TextField fieldPassword;

    @FXML
    private TextField fieldSecondName;

    @FXML
    private TextField fieldSeries;

    @FXML
    void initialize() {

        buttonDeleteAcc.setOnAction(event ->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Подтверждение удаления аккаунта");
//            alert.setHeaderText(null);
//            alert.setContentText("Вы точно ");
            alert.setHeaderText("Подтверждение");
            alert.setContentText("Вы уверены, что хотите удалить аккаунт?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {

            } else {

            }

        });

        buttonUpdateUser.setOnAction(event ->{
            String firstName = fieldFirstName.getText();
            String lastName = fieldSecondName.getText();
            String fatherName = fieldFatherName.getText();
            String series = fieldSeries.getText();
            String num = fieldNum.getText();
            String login = fieldLogin.getText();
            String password = fieldPassword.getText();

            if(!firstName.isEmpty() &&!lastName.isEmpty() && !fatherName.isEmpty()
            & !series.isEmpty() && !num.isEmpty() &&!login.isEmpty() &&!password.isEmpty()){
                updateClient(LoginData.currentLogin.getLogin(), firstName,lastName,fatherName,series,num,login,password);
            }
            else{
                Toast.makeToast((Stage) buttonUpdateUser.getScene().getWindow(), "Заполните все поля", Duration.seconds(2));
            }
        });

        buttonBackUser.setOnAction(event ->{
            FxMethods.changeStageFxml(getClass().getResource("client-view.fxml"),buttonBackUser);
        });

    }

    private void updateClient(final String oldLogin, final String firstname, final String lastname, final String fatherName,
                              final String passportSeries, final String passportNum, final String newLogin, final String password) {
        DataBaseHandler dbHandler = new DataBaseHandler();

        if(dbHandler.updateClient(oldLogin, firstname, lastname, fatherName, passportSeries, passportNum, newLogin, password)) {
            Toast.makeToast(FxMethods.changeStageFxml(getClass().getResource("client-view.fxml"), buttonUpdateUser), "информация обновлена",
                    Duration.seconds(2));
            LoginData.setCurrentLogin(new LoginData(newLogin,password));
        }
        else{
            Toast.makeToast((Stage) buttonUpdateUser.getScene().getWindow(), "Ошибка обновления", Duration.seconds(2));

        }
    }

}

