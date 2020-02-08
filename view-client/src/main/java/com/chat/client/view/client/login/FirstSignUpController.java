package com.chat.client.view.client.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstSignUpController implements Initializable {
    Stage stage;


    public void setStageSignUp(Stage myStage) {
        this.stage = myStage;
    }

    public void nextActionbtn(ActionEvent actionEvent) {
        Parent root;
        //;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/login/secondpagesignup.fxml"));
            root = loader.load();
            System.out.println(stage);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
