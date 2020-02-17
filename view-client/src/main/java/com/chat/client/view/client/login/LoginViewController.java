package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.chat.UserHome;
import com.chat.server.model.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    Stage stage;
    @FXML
    Label lblloginSignuphere;
    @FXML
    TextField txtFieldLoginPhone;

    @FXML
    PasswordField txtFieldloginPassword;

    SignUpAndRegistration signUpAndRegistration;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setStageLogin(Stage stage) {
        this.stage = stage;

    }

    @FXML
    private void onLogin(ActionEvent actionEvent) {
        user = signUpAndRegistration.login(txtFieldLoginPhone.getText(), txtFieldloginPassword.getText());
        System.out.println(user);
        if (user != null && user.getId() > 0) {
            System.out.println("login successfully");
            goToHomePage();
        } else {
            System.out.println("user not found");
        }
        String title = "sign in";
        // TrayNotification tray = new TrayNotification()
    }


    public void goToHomePage() {

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
}
