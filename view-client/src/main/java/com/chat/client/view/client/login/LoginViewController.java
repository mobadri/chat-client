package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.login.RememberMeController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.chat.UserHome;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    Stage stage;

    @FXML
    CheckBox rememberMe;
    @FXML
    Label lblloginSignuphere;
    @FXML
    TextField txtFieldLoginPhone;

    @FXML
    PasswordField txtFieldloginPassword;

    SignUpAndRegistration signUpAndRegistration;


    @FXML
    private Label invalidPhone;

    @FXML
    private Label invalidPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setStageLogin(Stage stage) {
        this.stage = stage;

    }

    public void login(){
        clearAffectOfValidation();
        if (validatePhone(txtFieldLoginPhone.getText().trim()) && existedPhone(txtFieldLoginPhone.getText().trim()) != null) {

            User user = signUpAndRegistration.login(txtFieldLoginPhone.getText().trim(), txtFieldloginPassword.getText().trim());
            System.out.println(user);
            if (user != null && user.getId() > 0) {
                System.out.println("login successfully");
                if(rememberMe.isSelected()){
                    isRememberMe();
                }
                goToHomePage(user);

            } else {
                System.out.println("user not found");
                affectInvalidPassword();

            }
        } else {

            affectInvalidPhone();
        }
        String title = "sign in";
    }
    @FXML
    private void onLogin(ActionEvent actionEvent) {
        clearAffectOfValidation();
        if (validatePhone(txtFieldLoginPhone.getText().trim()) && existedPhone(txtFieldLoginPhone.getText().trim()) != null) {

            User user = signUpAndRegistration.login(txtFieldLoginPhone.getText().trim(), txtFieldloginPassword.getText().trim());
            System.out.println(user);
            if (user != null && user.getId() > 0) {
                System.out.println("login successfully");
               if(rememberMe.isSelected()){
                   isRememberMe();
                }
                goToHomePage(user);

            } else {
                System.out.println("user not found");
                affectInvalidPassword();

            }
        } else {

            affectInvalidPhone();
        }
        String title = "sign in";
        // TrayNotification tray = new TrayNotification()
    }

    void setPbone(String name) {

    }

    public void goToHomePage(User user) {

        Parent root;
        //;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/templates/user/user-home.fxml"));
            root = loader.load();
            UserHome userHome = loader.getController();
            userHome.setCurrentUser(user);
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void setSignUpAndRegistration(SignUpAndRegistration signUpAndRegistration) {
        this.signUpAndRegistration = signUpAndRegistration;
    }

    @FXML
    public void onSignUp(MouseEvent mouseEvent) {
        System.out.println("Label pressed");
        Parent root;
        //;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/templates/login/firstsignup.fxml"));
            root = loader.load();
            FirstSignUpController firstSignUpController = loader.getController();
            firstSignUpController.setStageSignUp(stage);
            firstSignUpController.setSignUpAndRegistration(signUpAndRegistration);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void closeApp(MouseEvent mouseEvent) {
    }

    public void signUpView(ActionEvent actionEvent) {
    }

    public void signInView(ActionEvent actionEvent) {
    }


    public void isRememberMe(){
            RememberMeController rememberMeController = new RememberMeController();
            rememberMeController.CreateIntoXML(txtFieldLoginPhone.getText(), txtFieldloginPassword.getText());
            System.out.println(true);
    }


    private void affectInvalidPhone() {
        invalidPhone.setText("* Invalid phone Number");
        txtFieldLoginPhone.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
    }

    private void affectInvalidPassword() {
        invalidPassword.setText("* Invalid password");
        txtFieldloginPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
    }


    public User existedPhone(String phone) {
        return signUpAndRegistration.existedPhone(phone);
    }

    public boolean validatePhone(String phone) {
        return signUpAndRegistration.validatePhone(phone);
    }

    private void clearAffectOfValidation() {
        invalidPhone.setText("");
        txtFieldLoginPhone.setStyle("-fx-border-color:gray  ; -fx-border-width: 1px ;");

        invalidPassword.setText("");
        txtFieldloginPassword.setStyle("-fx-border-color: gray ; -fx-border-width: 1px ;");


    }


}
