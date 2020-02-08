package com.chat.client.view.client;

import com.chat.client.view.client.login.FirstSignUpController;
import com.chat.client.view.client.login.LoginController;
import javafx.event.ActionEvent;
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

    void setStage(Stage stage2) {
        this.stage = stage2;
        System.out.println("My Stage in Start Page Controller" + this.stage);
        System.out.println("Hey From setStage");
    }

    public void gotosignuppage(ActionEvent actionEvent) {
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

    }

    public void gotosigninpage(ActionEvent actionEvent) {
        Parent root;
        //;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/login.fxml"));
            root = loader.load();
            System.out.println(getClass().toString());
            System.out.println(getClass().getResource("/templates/login/login.fxml").getPath());
            System.out.println(stage);
            LoginController loginController = loader.getController();
            loginController.setStageLogin(stage);

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("My stage is = " + stage);
    }


}
