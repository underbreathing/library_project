package com.example.libraryproject;

import com.example.libraryproject.animations.Shake;
import com.example.libraryproject.db.DataBaseHandler;
import com.example.libraryproject.dbClasses.LoginData;
import com.example.libraryproject.fxMethods.FxMethods;
import com.example.libraryproject.fxMethods.Toast;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

    public class AuthController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button buttonLogin;

        @FXML
        private Button buttonRegister;

        @FXML
        private TextField loginField;

        @FXML
        private PasswordField passwordField;


        @FXML
        void registerButtonAction(ActionEvent event) {
            FxMethods.changeStageFxml(getClass().getResource("register-view.fxml"),buttonRegister);
        }

        @FXML
        void initialize() {
            buttonLogin.setOnAction(event ->{
                String login = loginField.getText();
                String password = passwordField.getText();
                if(!login.isEmpty() && !password.isEmpty()) {
                loginUser(login,password);
                }
                else{
                    System.out.println("не все поля для авторизации заполнены");
                    animAuth();
                }
            });
        }

        private void loginUser(final String login, final String password){
            if(login.equals("admin")  && password.equals("admin123")){
                System.out.println("Это Админ!");
                FxMethods.changeStageFxml(getClass().getResource("admin-main-view.fxml"),buttonLogin);
                return;
            }
                DataBaseHandler dbHandler = new DataBaseHandler();
                ResultSet resultSet = dbHandler.getLogin(login, password);
            try {
                if(resultSet.next()){
                    System.out.println("Пользователь авторизован!");
                    LoginData.setCurrentLogin(new LoginData(login,password));//фиксируем данные авторизовавшегося пользователя
                    FxMethods.changeStageFxml(getClass().getResource("client-view.fxml"),buttonLogin);
                }
                else {
                    Toast.makeToast((Stage) buttonLogin.getScene().getWindow(), "Неверный логин или пароль.", Duration.seconds(2));
                    animAuth();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        private void animAuth(){
            Shake userLoginAnim = new Shake(loginField);
            Shake userPasswordAnim = new Shake(passwordField);
            userLoginAnim.playAnim();
            userPasswordAnim.playAnim();
        }

    }

