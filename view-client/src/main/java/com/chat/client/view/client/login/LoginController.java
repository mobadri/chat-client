package com.chat.client.view.client.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Stage stage;
    @FXML
    Label lblloginSignuphere;

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

    public void goToHomePage(ActionEvent actionEvent) {
        Parent root;
        //;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/user/user-friends.fxml"));
            root = loader.load();
            System.out.println(stage);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}
