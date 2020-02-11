package com.chat.client.view.client;

import com.chat.client.controller.client.user.login.RegistrationController;
import com.chat.client.controller.client.user.login.SignUpAndRegistration;
import com.chat.client.view.client.login.FirstSignUpController;
import com.chat.client.view.client.login.LoginViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class startpageController implements Initializable {
    Stage stage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void gotosignuppage(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/firstsignup.fxml"));
            root = loader.load();
            System.out.println(stage);
            stage.setScene(new Scene(root));
            FirstSignUpController firstSignUpController = loader.getController();
            firstSignUpController.setStageSignUp(stage);
            firstSignUpController.setSignUpAndRegistration(new RegistrationController());
            //firstSignUpController
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    @FXML
    public void gotosigninpage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/login.fxml"));
            Parent root = loader.load();
            LoginViewController loginView = loader.getController();
            loginView.setStageLogin(stage);
            SignUpAndRegistration registrationController =
                    new RegistrationController();
            loginView.setSignUpAndRegistration(registrationController);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
