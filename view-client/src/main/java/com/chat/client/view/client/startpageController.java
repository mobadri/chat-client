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
    public void goToSignUpPage(ActionEvent actionEvent) {
        System.out.println("clicked");
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/testsignup.fxml"));
            root = loader.load();
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
    public void goToSignInPage(ActionEvent actionEvent) {
        System.out.println("clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/test.fxml"));
            Parent root = loader.load();
            System.out.println("loaded");
            LoginViewController loginView = loader.getController();
            loginView.setStageLogin(stage);
            SignUpAndRegistration registrationController =
                    new RegistrationController();
            loginView.setSignUpAndRegistration(registrationController);
            stage.setScene(new Scene(root));

            //stage.initStyle(StageStyle.TRANSPARENT);
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    class SplashScreen extends Thread {
        public void run() {
            try {
                Thread.sleep(5000);
                Parent root = FXMLLoader.load(getClass().getResource("/templates/login/test.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}