package com.chat.client.view.client.login;

import com.chat.client.controller.client.user.SignUpAndRegistration;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    @FXML
    Label lblloginSignuphere;
    @FXML
    TextField txtFieldLoginPhone;

    @FXML
    PasswordField txtFieldloginPassword;

    SignUpAndRegistration signUpAndRegistration;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblloginSignuphere.setOnMouseClicked(mouseEvent -> {
            System.out.println("Label pressed");
            Parent root;
            //;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/firstsignup.fxml"));
                root = loader.load();
                stage.setScene(new Scene(root));
                FirstSignUpController firstSignUpController = loader.getController();
                firstSignUpController.setStageSignUp(stage);

                //firstSignUpController
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }

        });
    }


    public void setStageLogin(Stage stage) {
        this.stage = stage;

    }

    @FXML
    private void onLogin(ActionEvent actionEvent) {
        User user = signUpAndRegistration.login(txtFieldLoginPhone.getText(), txtFieldloginPassword.getText());
        if (user != null && user.getId() > 0) {
            System.out.println("login successfully");
            goToHomePage();
        } else {
            System.out.println("user not found");
        }

    }


    public void goToHomePage() {

        Parent root;
        //;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/templates/user/user-friends.fxml"));
            root = loader.load();
            System.out.println(stage);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void setSignUpAndRegistration(SignUpAndRegistration signUpAndRegistration) {
        this.signUpAndRegistration = signUpAndRegistration;
    }
}
