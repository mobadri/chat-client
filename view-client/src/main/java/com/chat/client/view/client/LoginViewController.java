package com.chat.client.view.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginViewController {

    @FXML
    AnchorPane signInView;

    @FXML
    AnchorPane signUpView;

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
