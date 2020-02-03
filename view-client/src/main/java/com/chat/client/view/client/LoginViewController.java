package com.chat.client.view.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController {

    @FXML
    AnchorPane signInView;

    @FXML
    AnchorPane signUpView;

    @FXML
    AnchorPane parentPane;

    public void signUpView(ActionEvent actionEvent) {
        signUpView.toFront();
    }

    public void signInView(ActionEvent actionEvent) {
        signInView.toFront();
    }

    public void closeApp(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
